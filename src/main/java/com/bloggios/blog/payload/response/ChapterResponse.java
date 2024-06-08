package com.bloggios.blog.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.payload.response
 * Created_on - June 08 - 2024
 * Created_at - 13:45
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChapterResponse {

    private String chapterId;
    private String chapterName;
    private String coverImage;
}
