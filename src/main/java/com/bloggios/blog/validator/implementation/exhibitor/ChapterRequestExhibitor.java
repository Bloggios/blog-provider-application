package com.bloggios.blog.validator.implementation.exhibitor;

import com.bloggios.blog.payload.request.ChapterRequest;
import com.bloggios.blog.utils.AsyncUtils;
import com.bloggios.blog.validator.Exhibitor;
import com.bloggios.blog.validator.implementation.businessvalidator.ChapterNameValidator;
import com.bloggios.blog.validator.implementation.businessvalidator.CoverImageValidator;
import com.bloggios.blog.validator.implementation.businessvalidator.TopicsValidator;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.exhibitor
 * Created_on - June 02 - 2024
 * Created_at - 12:15
 */

@Component
public class ChapterRequestExhibitor implements Exhibitor<ChapterRequest> {

    private final ChapterNameValidator chapterNameValidator;
    private final CoverImageValidator coverImageValidator;
    private final TopicsValidator topicsValidator;

    public ChapterRequestExhibitor(ChapterNameValidator chapterNameValidator, CoverImageValidator coverImageValidator, TopicsValidator topicsValidator) {
        this.chapterNameValidator = chapterNameValidator;
        this.coverImageValidator = coverImageValidator;
        this.topicsValidator = topicsValidator;
    }

    @Override
    public void validate(ChapterRequest chapterRequest) {
        CompletableFuture<Void> chapterNameValidatorFuture = CompletableFuture.runAsync(() -> chapterNameValidator.validate(chapterRequest));
        CompletableFuture<Void> coverImageValidatorFuture = CompletableFuture.runAsync(() -> coverImageValidator.validate(chapterRequest.getCoverImage()));
        CompletableFuture<Void> topicsValidatorFuture = CompletableFuture.runAsync(() -> topicsValidator.validate(chapterRequest.getTopics()));
        AsyncUtils.getAsyncResult(CompletableFuture.allOf(
                chapterNameValidatorFuture,
                coverImageValidatorFuture,
                topicsValidatorFuture
        ));
    }
}
