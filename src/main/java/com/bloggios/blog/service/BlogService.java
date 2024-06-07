package com.bloggios.blog.service;

import com.bloggios.blog.payload.request.BlogListRequest;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.payload.response.BlogResponse;
import com.bloggios.blog.payload.response.ModuleResponse;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.service
 * Created_on - June 01 - 2024
 * Created_at - 15:07
 */

public interface BlogService {

    CompletableFuture<ModuleResponse> addBlog(BlogRequest blogRequest);
    CompletableFuture<ListResponse> blogList(BlogListRequest blogListRequest);
    CompletableFuture<ListResponse> unauthBlogList(
            Integer page, String userId, String topic
    );
    CompletableFuture<BlogResponse> getUnauthBlog(String blogId);
}
