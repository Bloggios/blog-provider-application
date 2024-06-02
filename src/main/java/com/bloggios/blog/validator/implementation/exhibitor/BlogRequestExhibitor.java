package com.bloggios.blog.validator.implementation.exhibitor;

import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.utils.AsyncUtils;
import com.bloggios.blog.validator.Exhibitor;
import com.bloggios.blog.validator.implementation.businessvalidator.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.exhibitor
 * Created_on - June 01 - 2024
 * Created_at - 16:23
 */

@Component
public class BlogRequestExhibitor implements Exhibitor<BlogRequest> {

    private final BlogDetailsTextValidator blogDetailsTextValidator;
    private final BlogImagesValidator blogImagesValidator;
    private final CoverImageValidator coverImageValidator;
    private final HtmlDetailsValidator htmlDetailsValidator;
    private final SchedulerTimeValidator schedulerTimeValidator;
    private final TitleValidator titleValidator;
    private final TopicsValidator topicsValidator;

    public BlogRequestExhibitor(
            BlogDetailsTextValidator blogDetailsTextValidator,
            BlogImagesValidator blogImagesValidator,
            CoverImageValidator coverImageValidator,
            HtmlDetailsValidator htmlDetailsValidator,
            SchedulerTimeValidator schedulerTimeValidator,
            TitleValidator titleValidator,
            TopicsValidator topicsValidator
    ) {
        this.blogDetailsTextValidator = blogDetailsTextValidator;
        this.blogImagesValidator = blogImagesValidator;
        this.coverImageValidator = coverImageValidator;
        this.htmlDetailsValidator = htmlDetailsValidator;
        this.schedulerTimeValidator = schedulerTimeValidator;
        this.titleValidator = titleValidator;
        this.topicsValidator = topicsValidator;
    }

    @Override
    public void validate(BlogRequest blogRequest) {
        CompletableFuture<Void> blogDetailsFuture = CompletableFuture.runAsync(() -> blogDetailsTextValidator.validate(blogRequest));
        CompletableFuture<Void> blogImagesFuture = CompletableFuture.runAsync(() -> blogImagesValidator.validate(blogRequest));
        CompletableFuture<Void> coverImageFuture = CompletableFuture.runAsync(() -> coverImageValidator.validate(blogRequest.getCoverImage()));
        CompletableFuture<Void> htmlDetailsFuture = CompletableFuture.runAsync(() -> htmlDetailsValidator.validate(blogRequest));
        CompletableFuture<Void> schedulerFuture = CompletableFuture.runAsync(() -> schedulerTimeValidator.validate(blogRequest));
        CompletableFuture<Void> titleFuture = CompletableFuture.runAsync(() -> titleValidator.validate(blogRequest.getTitle()));
        CompletableFuture<Void> topicsFuture = CompletableFuture.runAsync(() -> topicsValidator.validate(blogRequest.getTopics()));
        AsyncUtils.getAsyncResult(CompletableFuture.allOf(
                blogDetailsFuture,
                blogImagesFuture,
                coverImageFuture,
                htmlDetailsFuture,
                schedulerFuture,
                titleFuture,
                topicsFuture
        ));
    }
}
