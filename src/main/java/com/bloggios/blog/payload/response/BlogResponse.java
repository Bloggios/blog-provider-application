package com.bloggios.blog.payload.response;

import com.bloggios.blog.document.embeddable.ImageLinksNested;
import com.bloggios.blog.document.embeddable.TopicsNested;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.payload.response
 * Created_on - June 07 - 2024
 * Created_at - 21:21
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogResponse {

    private String name;
    private String email;
    private String profileTag;
    private String profileImage;
    private boolean isBadge;
    private String blogId;
    private String title;
    private String detailsHtml;
    private Object delta;
    private String detailsText;
    private Date dateCreated;
    private Date dateUpdated;
    private List<ImageLinksNested> imageLinks = new ArrayList<>();
    private List<TopicsNested> topics = new ArrayList<>();
    private String coverImage;
    private String seoTitle;
    private String canonicalUrl;
    private String chapterName;
}
