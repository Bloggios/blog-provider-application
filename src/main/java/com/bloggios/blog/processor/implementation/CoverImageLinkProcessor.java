package com.bloggios.blog.processor.implementation;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.file.UploadFile;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.payload.request.ChapterRequest;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.processor.implementation
 * Created_on - June 01 - 2024
 * Created_at - 20:01
 */

@Component
public class CoverImageLinkProcessor {

    private final UploadFile uploadFile;
    private final Environment environment;

    public CoverImageLinkProcessor(UploadFile uploadFile, Environment environment) {
        this.uploadFile = uploadFile;
        this.environment = environment;
    }

    public String process(BlogRequest blogRequest) {
        if (blogRequest.getCoverImage() == null) return null;
        String imageName = uploadFile.uploadImage(
                environment.getProperty(EnvironmentConstants.COVER_IMAGES_PATH),
                blogRequest.getCoverImage(),
                blogRequest.getAuthenticatedUser().getUserId()
        );
        String type = imageName.substring(imageName.lastIndexOf("."));
        return generateLink(imageName);
    }

    public String process(ChapterRequest chapterRequest) {
        if (chapterRequest.getCoverImage() == null) return null;
        String imageName = uploadFile.uploadImage(
                environment.getProperty(EnvironmentConstants.COVER_IMAGES_PATH),
                chapterRequest.getCoverImage(),
                chapterRequest.getAuthenticatedUser().getUserId()
        );
        String type = imageName.substring(imageName.lastIndexOf("."));
        return generateLink(imageName);
    }

    private String generateLink(String imageName) {
        String profile = environment.getProperty(EnvironmentConstants.APPLICATION_PROFILE);
        assert profile != null;
        String url = environment.getProperty(EnvironmentConstants.ASSETS);
        assert url != null;
        return url +
                "/" +
                ServiceConstants.COVER +
                "/" +
                LocalDate.now().getMonth().toString() +
                "/" +
                imageName;
    }
}
