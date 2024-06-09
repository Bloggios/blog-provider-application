package com.bloggios.blog.dao.implementation.esimplementation;

import com.bloggios.blog.dao.EsAbstractDao;
import com.bloggios.blog.dao.repository.elasticsearch.LikeDocumentRepository;
import com.bloggios.blog.document.LikeDocument;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.implementation.esimplementation
 * Created_on - June 09 - 2024
 * Created_at - 21:47
 */

@Component
public class LikeDocumentDao extends EsAbstractDao<LikeDocument, LikeDocumentRepository> {

    protected LikeDocumentDao(LikeDocumentRepository repository) {
        super(repository);
    }

    public Optional<LikeDocument> findByDestinationIdAndUserId(String destinationId, String userId) {
        return repository.findByDestinationIdAndUserId(destinationId, userId);
    }
}
