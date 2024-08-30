package com.bloggios.blog.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.constants.BeanConstants;
import com.bloggios.blog.dao.implementation.esimplementation.LikeDocumentDao;
import com.bloggios.blog.document.LikeDocument;
import com.bloggios.blog.payload.response.CountResponse;
import com.bloggios.blog.payload.response.LikeResponse;
import com.bloggios.blog.processor.implementation.DoLikeProcessor;
import com.bloggios.blog.processor.implementation.DoUnlikeProcessor;
import com.bloggios.blog.service.LikeService;
import com.bloggios.blog.utils.ValueCheckerUtil;
import com.bloggios.blog.validator.implementation.businessvalidator.LikeTypeStringValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
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
    private final DoUnlikeProcessor doUnlikeProcessor;
    private final DoLikeProcessor doLikeProcessor;

    @Override
    public CompletableFuture<LikeResponse> handleLike(String destinationId, String likeFor, AuthenticatedUser authenticatedUser) {
        ValueCheckerUtil.isValidUUID(destinationId);
        likeTypeStringValidator.validate(likeFor);
        Optional<LikeDocument> likeDocumentOptional = likeDocumentDao.findByDestinationIdAndUserId(destinationId, authenticatedUser.getUserId());
        LikeResponse likeResponse;
        if (likeDocumentOptional.isPresent()) {
            likeResponse = doUnlikeProcessor.process(likeDocumentOptional.get());
        } else {
            likeResponse = doLikeProcessor.process(destinationId, likeFor, authenticatedUser.getUserId());
        }
        return CompletableFuture.completedFuture(likeResponse);
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<CountResponse> countLikes(String destinationId) {
        ValueCheckerUtil.isValidUUID(destinationId);
        long likesCount = likeDocumentDao.countLikeDocumentByDestinationId(destinationId);
        return CompletableFuture.completedFuture(new CountResponse(likesCount));
    }
}
