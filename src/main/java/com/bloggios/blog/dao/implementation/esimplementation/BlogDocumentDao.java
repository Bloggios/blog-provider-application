package com.bloggios.blog.dao.implementation.esimplementation;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.dao.EsAbstractDao;
import com.bloggios.blog.dao.repository.elasticsearch.BlogDocumentRepository;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.elasticsearch.configuration.elasticdao.ElasticQuery;
import com.bloggios.elasticsearch.configuration.payload.ListRequest;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.implementation.esimplementation
 * Created_on - June 01 - 2024
 * Created_at - 20:25
 */

@Component
public class BlogDocumentDao extends EsAbstractDao<BlogDocument, BlogDocumentRepository> {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQuery elasticQuery;
    private final Environment environment;

    protected BlogDocumentDao(
            BlogDocumentRepository repository,
            ElasticsearchOperations elasticsearchOperations,
            ElasticQuery elasticQuery,
            Environment environment
    ) {
        super(repository);
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticQuery = elasticQuery;
        this.environment = environment;
    }

    public SearchHits<BlogDocument> blogDocumentSearchHits(ListRequest listRequest) {
        return elasticsearchOperations
                .search(elasticQuery.build(listRequest), BlogDocument.class, IndexCoordinates.of(
                        environment.getProperty(EnvironmentConstants.BLOG_INDEX)
                ));
    }

    public Optional<BlogDocument> findById(String blogId) {
        return repository.findById(blogId);
    }

    public long countByUserId(String userId) {
        return repository.countByUserId(userId);
    }
}
