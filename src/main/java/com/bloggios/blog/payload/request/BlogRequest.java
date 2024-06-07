package com.bloggios.blog.payload.request;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.payload.request
 * Created_on - June 01 - 2024
 * Created_at - 15:01
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogRequest {

    private List<MultipartFile> images;
    private String title;
    private String detailsHtml;
    private String detailsText;
    private List<String> topics;
    private Object delta;
    private Long milliseconds;
    private String chapterId;
    private MultipartFile coverImage;
    private AuthenticatedUser authenticatedUser;
    private HttpServletRequest httpServletRequest;
    private String seoTitle;
    private String canonicalUrl;
}
