package com.bloggios.blog.service;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.service
 * Created_on - August 30 - 2024
 * Created_at - 00:49
 */

public interface SchedulerService {

    CompletableFuture<ListResponse> getUserScheduler(AuthenticatedUser authenticatedUser);
}
