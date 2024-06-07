package com.bloggios.blog.document;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.document.embeddable.ImageLinksNested;
import com.bloggios.blog.document.embeddable.TopicsNested;
import com.bloggios.blog.enums.FeatureStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.document
 * Created_on - June 01 - 2024
 * Created_at - 15:50
 */

@Document(
        indexName = EnvironmentConstants.BLOG_ES_INDEX_GET_PROPERTY
)
@Setting(
        settingPath = EnvironmentConstants.ES_SETTING
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogDocument {

    @Id
    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String blogId;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = ServiceConstants.DEFAULT_AUTOCOMPLETE,
                    fielddata = true
            ),
            otherFields = {
                    @InnerField(
                            suffix = ServiceConstants.VERBATIM,
                            type = FieldType.Keyword,
                            normalizer = ServiceConstants.DEFAULT_NORMALIZER
                    )
            }
    )
    private String title;

    @Field(
            type = FieldType.Text
    )
    private String detailsHtml;

    @Field(
            type = FieldType.Auto
    )
    private Object delta;

    @Field(
            type = FieldType.Text
    )
    private String detailsText;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String userId;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String version;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String apiVersion;

    @Field(
            type = FieldType.Date
    )
    private Date dateCreated;

    @Field(
            type = FieldType.Date
    )
    private Date dateUpdated;

    @Field(
            type = FieldType.Date
    )
    private Date scheduledOn;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String remoteAddress;

    @Field(
            type = FieldType.Nested
    )
    private List<ImageLinksNested> imageLinks = new ArrayList<>();

    @Field(
            type = FieldType.Nested
    )
    private List<TopicsNested> topics = new ArrayList<>();

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String chapterId;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private FeatureStatus featureStatus;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String coverImage;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String seoTitle;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String canonicalUrl;
}
