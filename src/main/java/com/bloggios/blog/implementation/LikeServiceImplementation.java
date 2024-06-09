package com.bloggios.blog.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.dao.implementation.esimplementation.LikeDocumentDao;
import com.bloggios.blog.document.LikeDocument;
import com.bloggios.blog.payload.response.ModuleResponse;
import com.bloggios.blog.service.LikeService;
import com.bloggios.blog.utils.ValueCheckerUtil;
import com.bloggios.blog.validator.implementation.businessvalidator.LikeTypeStringValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.implementation
 * Created_on - June 09 - 2024
 * Created_at - 21:45
 */

@Service
@RequiredArgsConstructor
public class LikeServiceImplementation implements LikeService {

    private final LikeTypeStringValidator likeTypeStringValidator;
    private final LikeDocumentDao likeDocumentDao;

    @Override
    public CompletableFuture<ModuleResponse> handleLike(String destinationId, String likeFor, AuthenticatedUser authenticatedUser) {
        ValueCheckerUtil.isValidUUID(destinationId);
        likeTypeStringValidator.validate(likeFor);
        Optional<LikeDocument> likeDocumentOptional = likeDocumentDao.findByDestinationIdAndUserId(destinationId, authenticatedUser.getUserId());
        if (likeDocumentOptional.isPresent()) {
            // Do Unlike
        } else {
            // Do Like
        }
        return null;
    }
}
