package com.bloggios.blog.transformer.implementation;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.enums.FeatureStatus;
import com.bloggios.blog.modal.BlogEntity;
import com.bloggios.blog.modal.ChapterEntity;
import com.bloggios.blog.payload.record.BlogImagesAndHtmlRecord;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.transformer.utils.TopicsTransformUtil;
import com.bloggios.blog.utils.IpUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.transformer.implementation
 * Created_on - June 01 - 2024
 * Created_at - 20:07
 */

@Component
public class BlogRequestToBlogEntityTransformer {

    private final Environment environment;

    public BlogRequestToBlogEntityTransformer(Environment environment) {
        this.environment = environment;
    }

    public BlogEntity transform(
            BlogRequest blogRequest,
            BlogImagesAndHtmlRecord blogImagesAndHtmlRecord,
            String coverImageLink,
            ChapterEntity chapterEntity,
            String finalHtml
    ) {
        return BlogEntity
                .builder()
                .title(blogRequest.getTitle())
                .detailsHtml(finalHtml)
                .delta(blogRequest.getDelta())
                .detailsText(blogRequest.getDetailsText())
                .userId(blogRequest.getAuthenticatedUser().getUserId())
                .version(UUID.randomUUID().toString())
                .apiVersion(environment.getProperty(EnvironmentConstants.APPLICATION_VERSION))
                .dateCreated(new Date())
                .scheduledOn(getScheduledOn(blogRequest))
                .remoteAddress(IpUtils.getRemoteAddress(blogRequest.getHttpServletRequest()))
                .imageLinks(blogImagesAndHtmlRecord.imageLinks())
                .chapter(chapterEntity)
                .featureStatus(getFeatureStatus(blogRequest))
                .topics(TopicsTransformUtil.getTopicsEmbeddableList(blogRequest.getTopics()))
                .coverImage(coverImageLink)
                .build();
    }

    private FeatureStatus getFeatureStatus(BlogRequest blogRequest) {
        if (Objects.nonNull(blogRequest.getMilliseconds())) {
            return FeatureStatus.SCHEDULED;
        } else {
            return FeatureStatus.VISIBLE;
        }
    }
    private Date getScheduledOn(BlogRequest blogRequest) {
        FeatureStatus featureStatus = getFeatureStatus(blogRequest);
        if (featureStatus.equals(FeatureStatus.SCHEDULED)) {
            Date currentDate = Calendar.getInstance().getTime();
            return new Date(currentDate.getTime() + blogRequest.getMilliseconds());
        }
        return null;
    }
}
