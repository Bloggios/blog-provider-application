package com.bloggios.blog.dao.implementation.esimplementation;

import com.bloggios.blog.dao.EsAbstractDao;
import com.bloggios.blog.dao.repository.elasticsearch.BlogDocumentRepository;
import com.bloggios.blog.document.BlogDocument;
import org.springframework.stereotype.Component;

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

    protected BlogDocumentDao(BlogDocumentRepository repository) {
        super(repository);
    }
}
