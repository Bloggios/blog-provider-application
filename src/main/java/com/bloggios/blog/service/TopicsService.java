package com.bloggios.blog.service;

import com.bloggios.blog.payload.response.TopicsListResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.service
 * Created_on - May 29 - 2024
 * Created_at - 21:00
 */

public interface TopicsService {

    CompletableFuture<TopicsListResponse> tagsList();
}
