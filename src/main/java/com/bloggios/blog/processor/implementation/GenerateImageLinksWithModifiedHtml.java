package com.bloggios.blog.processor.implementation;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.modal.embeddable.ImageLinksEntity;
import com.bloggios.blog.payload.record.BlogImagesAndHtmlRecord;
import com.bloggios.blog.payload.record.UploadImagePayloadRecord;
import com.bloggios.blog.payload.request.BlogRequest;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.processor
 * Created_on - June 01 - 2024
 * Created_at - 19:30
 */

@Component
public class GenerateImageLinksWithModifiedHtml {

    private final UploadImagesLinkProcessor uploadImagesLinkProcessor;
    private final Environment environment;
    private final AddImageLinksHtmlProcessor addImageLinksHtmlProcessor;

    public GenerateImageLinksWithModifiedHtml(
            UploadImagesLinkProcessor uploadImagesLinkProcessor,
            Environment environment, AddImageLinksHtmlProcessor addImageLinksHtmlProcessor) {
        this.uploadImagesLinkProcessor = uploadImagesLinkProcessor;
        this.environment = environment;
        this.addImageLinksHtmlProcessor = addImageLinksHtmlProcessor;
    }

    public BlogImagesAndHtmlRecord process(BlogRequest blogRequest) {
        List<ImageLinksEntity> imageLinks;
        String modifiedHtml;
        if (Objects.nonNull(blogRequest.getImages())) {
            imageLinks= uploadImagesLinkProcessor.apply(
                    new UploadImagePayloadRecord(
                            environment.getProperty(EnvironmentConstants.BLOG_IMAGES_PATH),
                            blogRequest.getAuthenticatedUser().getUserId(),
                            blogRequest.getImages(),
                            ServiceConstants.BLOG_IMAGE
                    )
            );
            modifiedHtml = addImageLinksHtmlProcessor.process(blogRequest.getDetailsHtml(), imageLinks);
        } else {
            imageLinks = null;
            modifiedHtml = blogRequest.getDetailsHtml();
        }
        return new BlogImagesAndHtmlRecord(
                imageLinks,
                modifiedHtml
        );
    }
}
