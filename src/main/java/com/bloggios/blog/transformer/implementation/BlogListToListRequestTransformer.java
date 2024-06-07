package com.bloggios.blog.transformer.implementation;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.payload.request.BlogListRequest;
import com.bloggios.blog.transformer.Transform;
import com.bloggios.blog.utils.AsyncUtils;
import com.bloggios.blog.utils.ListRequestUtils;
import com.bloggios.blog.ymlparser.listparser.BlogYmlFileParser;
import com.bloggios.elasticsearch.configuration.constants.ElasticServiceConstants;
import com.bloggios.elasticsearch.configuration.payload.ListRequest;
import com.bloggios.elasticsearch.configuration.payload.YamlListDataProvider;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Filter;
import com.bloggios.elasticsearch.configuration.payload.lspayload.SearchFilter;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.transformer.implementation
 * Created_on - May 27 - 2024
 * Created_at - 12:55
 */

@Component
@RequiredArgsConstructor
public class BlogListToListRequestTransformer implements Transform<ListRequest, BlogListRequest> {

    private final BlogYmlFileParser blogYmlFileParser;

    @Override
    public ListRequest transform(BlogListRequest profileListRequest) {
        Map<String, YamlListDataProvider> provider = blogYmlFileParser.getProvider();
        CompletableFuture<List<Filter>> filterListCompletableFuture = CompletableFuture.supplyAsync(() -> filterTransform(profileListRequest, provider));
        CompletableFuture<List<Sort>> sortListCompletableFuture = CompletableFuture.supplyAsync(() -> transformSort(profileListRequest, provider));
        Function<BlogListRequest, List<String>> getSearch = commentList ->
                Optional.ofNullable(profileListRequest.getTexts())
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(Objects::nonNull)
                        .toList();
        List<String> searchTexts = getSearch.apply(profileListRequest);
        ListRequest listRequest = new ListRequest();
        AsyncUtils.getAsyncResult(CompletableFuture.allOf(filterListCompletableFuture, sortListCompletableFuture));
        listRequest.setFilters(filterListCompletableFuture.join());
        listRequest.setSort(sortListCompletableFuture.join());
        if (!CollectionUtils.isEmpty(searchTexts)) {
            List<SearchFilter> searchFilterNgrams = transformSearch(profileListRequest, provider);
            listRequest.setTexts(searchTexts);
            listRequest.setField(searchFilterNgrams);
        }
        if (Objects.nonNull(profileListRequest.getPage())) listRequest.setPage(profileListRequest.getPage());
        if (Objects.nonNull(profileListRequest.getSize())) listRequest.setSize(profileListRequest.getSize());
        return listRequest;
    }

    private List<Filter> filterTransform(BlogListRequest profileListRequest, Map<String, YamlListDataProvider> provider) {
        List<Filter> filters = new ArrayList<>();
        if (Objects.nonNull(profileListRequest.getFilters())) {
            profileListRequest
                    .getFilters()
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(filter -> {
                        String columnName = null;
                        String nestedPath = null;
                        String filterKeyType = null;
                        Boolean partialSearch = true;
                        if (provider.containsKey(filter.getFilterKey())) {
                            YamlListDataProvider value = provider.get(filter.getFilterKey());
                            columnName = value.getFilterField();
                            nestedPath = value.getInnerPath();
                            filterKeyType = value.getDataType();
                            if (!ObjectUtils.isEmpty(value.getPartialSearch())) {
                                partialSearch = value.getPartialSearch();
                            } else {
                                partialSearch = ElasticServiceConstants.STRING.equalsIgnoreCase(value.getDataType());
                            }
                        } else {
                            throw new BadRequestException(DataErrorCodes.FILTER_KEY_NOT_VALID);
                        }
                        filters.add(ListRequestUtils.getFilter(filter.getSelections(), columnName, nestedPath, filterKeyType, partialSearch));
                    });
        }
        return filters;
    }

    public List<SearchFilter> transformSearch(BlogListRequest tagListRequest, Map<String, YamlListDataProvider> provider) {
        List<SearchFilter> searchFilters = new ArrayList<>();

        provider.forEach((key, value) -> {
            if (Boolean.TRUE.equals(value.getSearchAllowed())) {
                Boolean partialSearch = null;
                if (!ObjectUtils.isEmpty(value.getPartialSearch())) {
                    partialSearch = value.getPartialSearch();
                } else {
                    partialSearch = false;
                }
                searchFilters.add(ListRequestUtils.getSearchFilter(value.getSearchField(), value.getInnerPath(), partialSearch));
            }
        });
        return searchFilters;
    }

    private List<Sort> transformSort(BlogListRequest profileListRequest, Map<String, YamlListDataProvider> provider) {
        List<Sort> sorts = new ArrayList<>();
        if (!ObjectUtils.isEmpty(profileListRequest.getSorts()) && !profileListRequest.getSorts().isEmpty()) {
            profileListRequest
                    .getSorts()
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(sort -> {
                        String columnName = null;
                        String nestedPath = null;
                        if (provider.containsKey(sort.getSortKey())) {
                            YamlListDataProvider value = provider.get(sort.getSortKey());
                            columnName = value.getSortField();
                            nestedPath = value.getInnerPath();
                        } else {
                            throw new BadRequestException(DataErrorCodes.SORT_KEY_NOT_VALID);
                        }
                        sorts.add(ListRequestUtils.getSort(sort, columnName, nestedPath));
                    });
        } else {
            return ListRequestUtils.getSort(ServiceConstants.DATE_CREATED);
        }
        return sorts;
    }
}
