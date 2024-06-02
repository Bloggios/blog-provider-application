package com.bloggios.blog.payload.request;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.payload.request
 * Created_on - June 02 - 2024
 * Created_at - 12:12
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChapterRequest {

    private String chapterName;
    private MultipartFile coverImage;
    private AuthenticatedUser authenticatedUser;
    private List<String> topics;
}
