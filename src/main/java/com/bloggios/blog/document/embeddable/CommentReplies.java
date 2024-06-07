package com.bloggios.blog.document.embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.document.embeddable
 * Created_on - June 04 - 2024
 * Created_at - 14:33
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentReplies {

    private String reply;
    private String userId;
    private Date dateCreated;
    private String mentionedUser;
}
