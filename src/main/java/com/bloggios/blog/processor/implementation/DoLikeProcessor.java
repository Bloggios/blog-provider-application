package com.bloggios.blog.processor.implementation;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.constants.ResponseMessageConstants;
import com.bloggios.blog.dao.implementation.esimplementation.LikeDocumentDao;
import com.bloggios.blog.document.LikeDocument;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.enums.LikeType;
import com.bloggios.blog.payload.response.LikeResponse;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.processor.implementation
 * Created_on - June 09 - 2024
 * Created_at - 22:31
 */

@Component
public class DoLikeProcessor {

    private final Environment environment;
    private final LikeDocumentDao likeDocumentDao;

    public DoLikeProcessor(Environment environment, LikeDocumentDao likeDocumentDao) {
        this.environment = environment;
        this.likeDocumentDao = likeDocumentDao;
    }

    public LikeResponse process(String destinationId, String likeFor, String userId) {
        LikeDocument likeDocument = LikeDocument
                .builder()
                .likeId(UUID.randomUUID().toString())
                .userId(userId)
                .destinationId(destinationId)
                .likeType(LikeType.valueOf(likeFor))
                .apiVersion(environment.getProperty(EnvironmentConstants.APPLICATION_VERSION))
                .dateCreated(new Date())
                .build();
        likeDocumentDao.initOperation(DaoStatus.CREATE, likeDocument);
        return new LikeResponse(ResponseMessageConstants.LIKED, true);
    }
}
