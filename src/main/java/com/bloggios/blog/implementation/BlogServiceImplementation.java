package com.bloggios.blog.implementation;

import com.bloggios.blog.constants.BeanConstants;
import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.constants.ResponseMessageConstants;
import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.dao.implementation.esimplementation.BlogDocumentDao;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.BlogEntityDao;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.ChapterEntityDao;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.enums.FeatureStatus;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.modal.BlogEntity;
import com.bloggios.blog.modal.ChapterEntity;
import com.bloggios.blog.payload.record.BlogImagesAndHtmlRecord;
import com.bloggios.blog.payload.request.BlogListRequest;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.payload.response.BlogResponse;
import com.bloggios.blog.payload.response.BlogResponseForList;
import com.bloggios.blog.payload.response.ModuleResponse;
import com.bloggios.blog.persistence.BlogEntityToDocumentPersistence;
import com.bloggios.blog.processor.implementation.CoverImageLinkProcessor;
import com.bloggios.blog.processor.implementation.GenerateImageLinksWithModifiedHtml;
import com.bloggios.blog.processor.implementation.HtmlDataManipulation;
import com.bloggios.blog.service.BlogService;
import com.bloggios.blog.transformer.implementation.BlogDocumentToBlogResponseForListTransformer;
import com.bloggios.blog.transformer.implementation.BlogDocumentToBlogResponseTransformer;
import com.bloggios.blog.transformer.implementation.BlogListToListRequestTransformer;
import com.bloggios.blog.transformer.implementation.BlogRequestToBlogEntityTransformer;
import com.bloggios.blog.utils.ValueCheckerUtil;
import com.bloggios.blog.validator.implementation.businessvalidator.TopicsValidator;
import com.bloggios.blog.validator.implementation.exhibitor.BlogRequestExhibitor;
import com.bloggios.elasticsearch.configuration.payload.ListRequest;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Filter;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.implementation
 * Created_on - June 01 - 2024
 * Created_at - 15:08
 */

@Service
public class BlogServiceImplementation implements BlogService {

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImplementation.class);

    private final BlogRequestExhibitor blogRequestExhibitor;
    private final GenerateImageLinksWithModifiedHtml generateImageLinksWithModifiedHtml;
    private final CoverImageLinkProcessor coverImageLinkProcessor;
    private final ChapterEntityDao chapterEntityDao;
    private final BlogRequestToBlogEntityTransformer blogRequestToBlogEntityTransformer;
    private final BlogEntityDao blogEntityDao;
    private final BlogEntityToDocumentPersistence blogEntityToDocumentPersistence;
    private final HtmlDataManipulation htmlDataManipulation;
    private final BlogListToListRequestTransformer blogListToListRequestTransformer;
    private final BlogDocumentDao blogDocumentDao;
    private final TopicsValidator topicsValidator;
    private final BlogDocumentToBlogResponseForListTransformer blogDocumentToBlogResponseForListTransformer;
    private final BlogDocumentToBlogResponseTransformer blogDocumentToBlogResponseTransformer;

    public BlogServiceImplementation(
            BlogRequestExhibitor blogRequestExhibitor,
            GenerateImageLinksWithModifiedHtml generateImageLinksWithModifiedHtml,
            CoverImageLinkProcessor coverImageLinkProcessor,
            ChapterEntityDao chapterEntityDao,
            BlogRequestToBlogEntityTransformer blogRequestToBlogEntityTransformer,
            BlogEntityDao blogEntityDao,
            BlogEntityToDocumentPersistence blogEntityToDocumentPersistence,
            HtmlDataManipulation htmlDataManipulation, BlogListToListRequestTransformer blogListToListRequestTransformer, BlogDocumentDao blogDocumentDao, TopicsValidator topicsValidator, BlogDocumentToBlogResponseForListTransformer blogDocumentToBlogResponseForListTransformer, BlogDocumentToBlogResponseTransformer blogDocumentToBlogResponseTransformer) {
        this.blogRequestExhibitor = blogRequestExhibitor;
        this.generateImageLinksWithModifiedHtml = generateImageLinksWithModifiedHtml;
        this.coverImageLinkProcessor = coverImageLinkProcessor;
        this.chapterEntityDao = chapterEntityDao;
        this.blogRequestToBlogEntityTransformer = blogRequestToBlogEntityTransformer;
        this.blogEntityDao = blogEntityDao;
        this.blogEntityToDocumentPersistence = blogEntityToDocumentPersistence;
        this.htmlDataManipulation = htmlDataManipulation;
        this.blogListToListRequestTransformer = blogListToListRequestTransformer;
        this.blogDocumentDao = blogDocumentDao;
        this.topicsValidator = topicsValidator;
        this.blogDocumentToBlogResponseForListTransformer = blogDocumentToBlogResponseForListTransformer;
        this.blogDocumentToBlogResponseTransformer = blogDocumentToBlogResponseTransformer;
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ModuleResponse> addBlog(BlogRequest blogRequest) {
        long startTime = System.currentTimeMillis();
        blogRequestExhibitor.validate(blogRequest);
        ChapterEntity chapterEntity = null;
        if (StringUtils.hasText(blogRequest.getChapterId())) {
            chapterEntity = chapterEntityDao.findByChapterId(blogRequest.getChapterId())
                    .orElseThrow(() -> new BadRequestException(DataErrorCodes.CHAPTER_ID_NOT_FOUND));
        }
        BlogImagesAndHtmlRecord imagesAndHtmlRecord = generateImageLinksWithModifiedHtml.process(blogRequest);
        String coverImageLink = coverImageLinkProcessor.process(blogRequest);
        String finalHtml = htmlDataManipulation.process(imagesAndHtmlRecord.modifiedHtml());
        BlogEntity blogEntity = blogRequestToBlogEntityTransformer.transform(blogRequest, imagesAndHtmlRecord, coverImageLink, chapterEntity, finalHtml);
        BlogEntity blogEntityResponse = blogEntityDao.initOperation(DaoStatus.CREATE, blogEntity);
        BlogDocument blogDocument = blogEntityToDocumentPersistence.persist(blogEntityResponse, DaoStatus.CREATE);
        logger.info("Execution time (Add Blog) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ModuleResponse
                        .builder()
                        .userId(blogDocument.getUserId())
                        .message(ResponseMessageConstants.BLOG_CREATED)
                        .id(blogDocument.getBlogId())
                        .build()
        );
    }

    @Override
    public CompletableFuture<ListResponse> blogList(BlogListRequest blogListRequest) {
        long startTime = System.currentTimeMillis();
        if (Objects.isNull(blogListRequest)) throw new BadRequestException(DataErrorCodes.BLOG_LIST_REQUEST_NULL);
        ListRequest transform = blogListToListRequestTransformer.transform(blogListRequest);
        SearchHits<BlogDocument> searchHits = blogDocumentDao.blogDocumentSearchHits(transform);
        List<BlogDocument> list = new ArrayList<>();
        if (Objects.nonNull(searchHits)) {
            list = searchHits
                    .stream()
                    .map(SearchHit::getContent)
                    .toList();
        }
        logger.info("Execution time (Blog List) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ListResponse
                        .builder()
                        .page(blogListRequest.getPage())
                        .size(blogListRequest.getSize())
                        .pageSize(list.size())
                        .totalRecordsCount(searchHits!=null ? searchHits.getTotalHits() : 0)
                        .object(list)
                        .build());
    }

    @Override
    public CompletableFuture<ListResponse> unauthBlogList(Integer page, String userId, String topic) {
        long startTime = System.currentTimeMillis();
        List<Filter> filters = new ArrayList<>();
        if (StringUtils.hasText(userId)) {
            ValueCheckerUtil.isValidUUID(userId);
            Filter userIdFilter = Filter
                    .builder()
                    .filterKey(ServiceConstants.USER_ID_KEY)
                    .selections(List.of(userId))
                    .build();
            filters.add(userIdFilter);
        }
        if (StringUtils.hasText(topic)) {
            topicsValidator.validate(List.of(topic));
            Filter topicValidator = Filter
                    .builder()
                    .filterKey(ServiceConstants.TOPIC_KEY)
                    .selections(List.of(topic))
                    .build();
            filters.add(topicValidator);
        }
        Filter featureStatusFilter = Filter
                .builder()
                .filterKey(ServiceConstants.FEATURE_STATUS_KEY)
                .selections(List.of(FeatureStatus.VISIBLE.name()))
                .build();
        filters.add(featureStatusFilter);
        BlogListRequest blogListRequest = BlogListRequest
                .builder()
                .page(page)
                .size(10)
                .filters(filters)
                .build();
        ListRequest listRequest = blogListToListRequestTransformer.transform(blogListRequest);
        SearchHits<BlogDocument> searchHits = blogDocumentDao.blogDocumentSearchHits(listRequest);
        List<BlogResponseForList> list = new ArrayList<>();
        if (Objects.nonNull(searchHits)) {
            list = searchHits
                    .stream().parallel()
                    .map(SearchHit::getContent)
                    .map(blogDocumentToBlogResponseForListTransformer::transform)
                    .toList();
        }
        logger.info("Execution time (Unauth Blog List) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ListResponse
                        .builder()
                        .page(blogListRequest.getPage())
                        .size(blogListRequest.getSize())
                        .pageSize(list.size())
                        .totalRecordsCount(searchHits!=null ? searchHits.getTotalHits() : 0)
                        .object(list)
                        .build());
    }

    @Override
    public CompletableFuture<BlogResponse> getUnauthBlog(String blogId) {
        long startTime = System.currentTimeMillis();
        ValueCheckerUtil.isValidUUID(blogId);
        BlogDocument blogDocument = blogDocumentDao.findById(blogId)
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.BLOG_NOT_FOUND));
        if (blogDocument.getFeatureStatus().equals(FeatureStatus.SCHEDULED))
            throw new BadRequestException(DataErrorCodes.BLOG_NOT_FOUND);
        BlogResponse transform = blogDocumentToBlogResponseTransformer.transform(blogDocument);
        logger.info("Execution time (Get Unauth Blog) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(transform);
    }
}
