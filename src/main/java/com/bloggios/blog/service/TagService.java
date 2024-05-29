package com.bloggios.blog.service;

import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.service
 * Created_on - May 29 - 2024
 * Created_at - 21:00
 */

public interface TagService {

//    object: [
//           {
//              tag: <tag>,
//                category: <category>
//           }
//    ]
    CompletableFuture<ListResponse> tagsList();
}
