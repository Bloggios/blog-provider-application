package com.bloggios.blog.transformer.implementation;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.modal.ChapterEntity;
import com.bloggios.blog.payload.request.ChapterRequest;
import com.bloggios.blog.transformer.utils.TopicsTransformUtil;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.transformer.implementation
 * Created_on - June 02 - 2024
 * Created_at - 12:26
 */

@Component
public class ChapterRequestToEntityTransformer {

    private final Environment environment;

    public ChapterRequestToEntityTransformer(Environment environment) {
        this.environment = environment;
    }

    public ChapterEntity transform(ChapterRequest chapterRequest, String coverImage) {
        return ChapterEntity
                .builder()
                .chapterName(chapterRequest.getChapterName())
                .userId(chapterRequest.getAuthenticatedUser().getUserId())
                .topics(TopicsTransformUtil.getTopicsEmbeddableList(chapterRequest.getTopics()))
                .dateCreated(new Date())
                .apiVersion(UUID.randomUUID().toString())
                .version(environment.getProperty(EnvironmentConstants.APPLICATION_VERSION))
                .coverImage(coverImage)
                .build();
    }
}
