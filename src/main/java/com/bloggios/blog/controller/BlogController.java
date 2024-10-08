package com.bloggios.blog.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.constants.EndpointConstants;
import com.bloggios.blog.payload.request.BlogListRequest;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.payload.response.ExceptionResponse;
import com.bloggios.blog.payload.response.ModuleResponse;
import com.bloggios.blog.service.BlogService;
import com.bloggios.blog.utils.AsyncUtils;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    private final BlogService blogService;

    public BlogController(
            BlogService blogService
    ) {
        this.blogService = blogService;
    }

    @PostMapping
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ModuleResponse.class)
                    )),
                    @ApiResponse(description = "No Content", responseCode = "401", content = {
                            @Content(schema = @Schema(implementation = Void.class))
                    }),
                    @ApiResponse(description = "FORBIDDEN", responseCode = "403", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(description = "BAD REQUEST", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(
                            name = "bearerAuth"
                    )
            }
    )
    public ResponseEntity<ModuleResponse> addBlog(
            @RequestPart(required = false) List<MultipartFile> images,
            @RequestParam(required = true) String title,
            @RequestParam(required = true) String detailsHtml,
            @RequestParam(required = false) String detailsText,
            @RequestParam(required = false) List<String> topics,
            @RequestParam(required = false) Object delta,
            @RequestParam(required = false) Long milliseconds,
            @RequestParam(required = false) String chapterId,
            @RequestPart(required = false) MultipartFile coverImage,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            HttpServletRequest httpServletRequest,
            @RequestParam(required = false) String seoTitle,
            @RequestParam(required = false) String canonicalUrl
    ) {
        BlogRequest build = BlogRequest
                .builder()
                .images(images)
                .title(title)
                .detailsHtml(detailsHtml)
                .detailsText(detailsText)
                .topics(topics)
                .delta(delta)
                .milliseconds(milliseconds)
                .chapterId(chapterId)
                .coverImage(coverImage)
                .authenticatedUser(authenticatedUser)
                .httpServletRequest(httpServletRequest)
                .seoTitle(seoTitle)
                .canonicalUrl(canonicalUrl)
                .build();
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(blogService.addBlog(build)));
    }

    @PostMapping(EndpointConstants.BlogController.BLOG_LIST)
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ListResponse.class)
                    )),
                    @ApiResponse(description = "No Content", responseCode = "401", content = {
                            @Content(schema = @Schema(implementation = Void.class))
                    }),
                    @ApiResponse(description = "FORBIDDEN", responseCode = "403", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(description = "BAD REQUEST", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(
                            name = "bearerAuth"
                    )
            }
    )
    public ResponseEntity<ListResponse> getBlogList(@RequestBody BlogListRequest blogListRequest) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(blogService.blogList(blogListRequest)));
    }
}
