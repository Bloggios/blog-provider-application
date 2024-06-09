package com.bloggios.blog.processor.implementation;

import com.bloggios.blog.constants.ResponseMessageConstants;
import com.bloggios.blog.dao.implementation.esimplementation.LikeDocumentDao;
import com.bloggios.blog.document.LikeDocument;
import com.bloggios.blog.payload.response.LikeResponse;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.processor.implementation
 * Created_on - June 09 - 2024
 * Created_at - 22:25
 */

@Component
public class DoUnlikeProcessor {

    private final LikeDocumentDao likeDocumentDao;

    public DoUnlikeProcessor(LikeDocumentDao likeDocumentDao) {
        this.likeDocumentDao = likeDocumentDao;
    }

    public LikeResponse process(LikeDocument likeDocument) {
        likeDocumentDao.deleteByDocument(likeDocument);
        return new LikeResponse(ResponseMessageConstants.UNLIKED, false);
    }
}
