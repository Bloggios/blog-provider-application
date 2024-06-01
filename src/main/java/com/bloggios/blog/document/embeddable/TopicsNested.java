package com.bloggios.blog.document.embeddable;

import com.bloggios.blog.constants.ServiceConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import javax.persistence.Embeddable;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.modal.embeddable
 * Created_on - June 01 - 2024
 * Created_at - 15:30
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicsNested {

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
    private String topic;
}
