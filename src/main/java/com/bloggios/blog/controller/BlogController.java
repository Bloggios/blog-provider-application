package com.bloggios.blog.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.constants.EndpointConstants;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.payload.response.ModuleResponse;
import com.bloggios.blog.service.BlogService;
import com.bloggios.blog.utils.AsyncUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.controller
 * Created_on - June 01 - 2024
 * Created_at - 14:44
 */

@RestController
@RequestMapping(EndpointConstants.BlogController.BASE_PATH)
public class BlogController {

    private final BlogService blogService;

    public BlogController(
            BlogService blogService
    ) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<ModuleResponse> addBlog(
            @RequestPart(required = false) List<MultipartFile> images,
            @RequestPart(required = true) String title,
            @RequestPart(required = true) String detailsHtml,
            @RequestPart(required = false) String detailsText,
            @RequestPart(required = false) List<String> topics,
            @RequestPart(required = true) Object delta,
            @RequestPart(required = false) boolean isDraft,
            @RequestPart(required = false) Long milliseconds,
            @RequestPart(required = false) String chapterId,
            @RequestPart(required = false) MultipartFile coverImage,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            HttpServletRequest httpServletRequest
            ) {
        BlogRequest build = BlogRequest
                .builder()
                .images(images)
                .title(title)
                .detailsHtml(detailsHtml)
                .detailsText(detailsText)
                .topics(topics)
                .delta(delta)
                .isDraft(isDraft)
                .milliseconds(milliseconds)
                .chapterId(chapterId)
                .coverImage(coverImage)
                .authenticatedUser(authenticatedUser)
                .httpServletRequest(httpServletRequest)
                .build();
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(blogService.addBlog(build)));
    }
}
