package com.bloggios.blog.document;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.document.embeddable.CommentReplies;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.document
 * Created_on - June 04 - 2024
 * Created_at - 13:17
 */

@Document(
        indexName = EnvironmentConstants.COMMENT_ES_INDEX_GET_PROPERTY
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
public class CommentDocument {

    @Id
    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String commentId;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = ServiceConstants.DEFAULT_NORMALIZER,
                    fielddata = true
            ),
            otherFields = {
                    @InnerField(
                            suffix = ServiceConstants.VERBATIM,
                            type = FieldType.Keyword,
                            normalizer = ServiceConstants.DEFAULT_AUTOCOMPLETE
                    )
            }
    )
    private String comment;

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
            type = FieldType.Keyword
    )
    private Date dateUpdated;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String postId;

    @Field(
            type = FieldType.Nested
    )
    private List<CommentReplies> replies;

    private List<String> mentions;
}
