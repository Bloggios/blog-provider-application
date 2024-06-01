package com.bloggios.blog.dao.implementation.esimplementation;

import com.bloggios.blog.dao.EsAbstractDao;
import com.bloggios.blog.dao.repository.elasticsearch.ChapterDocumentRepository;
import com.bloggios.blog.document.ChapterDocument;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.implementation.esimplementation
 * Created_on - June 01 - 2024
 * Created_at - 20:26
 */

@Component
public class ChapterDocumentDao extends EsAbstractDao<ChapterDocument, ChapterDocumentRepository> {

    protected ChapterDocumentDao(ChapterDocumentRepository repository) {
        super(repository);
    }
}
