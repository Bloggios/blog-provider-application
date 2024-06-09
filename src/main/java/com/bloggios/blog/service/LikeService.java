package com.bloggios.blog.service;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.payload.response.ModuleResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.service
 * Created_on - June 09 - 2024
 * Created_at - 21:44
 */

public interface LikeService {

    CompletableFuture<ModuleResponse> handleLike(String destinationId, String likeFor, AuthenticatedUser authenticatedUser);
}
