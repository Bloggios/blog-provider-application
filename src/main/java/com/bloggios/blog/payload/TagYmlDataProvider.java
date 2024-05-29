package com.bloggios.blog.payload;

import lombok.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.payload
 * Created_on - May 29 - 2024
 * Created_at - 20:55
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagYmlDataProvider {

    private String tag;
    private String category;
}
