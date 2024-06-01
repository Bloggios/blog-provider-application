package com.bloggios.blog.payload.response;

import com.bloggios.blog.payload.TopicsYmlDataProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.payload.response
 * Created_on - May 30 - 2024
 * Created_at - 21:10
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicsListResponse {

    private List<TopicsYmlDataProvider> object;
}
