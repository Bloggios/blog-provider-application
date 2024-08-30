package com.bloggios.blog.controller;

import com.bloggios.blog.constants.EndpointConstants;
import com.bloggios.blog.payload.response.*;
import com.bloggios.blog.service.BlogService;
import com.bloggios.blog.service.LikeService;
import com.bloggios.blog.utils.AsyncUtils;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.controller
 * Created_on - June 07 - 2024
 * Created_at - 15:58
 */

@RestController
@RequestMapping(EndpointConstants.OpenController.BASE_PATH)
@RequiredArgsConstructor
public class OpenController {

    private final BlogService blogService;
    private final LikeService likeService;

    @GetMapping(EndpointConstants.OpenController.BlogData.BLOGS_LIST)
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
            }
    )
    public ResponseEntity<ListResponse> blogsList(
            @RequestParam Integer page,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String topic
    ) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(blogService.unauthBlogList(
                page, userId, topic
        )));
    }

    @GetMapping(EndpointConstants.OpenController.BlogData.BASE_PATH)
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = BlogResponse.class)
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
            }
    )
    public ResponseEntity<BlogResponse> getUnauthBlog(
            @RequestParam String blogId
    ) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(blogService.getUnauthBlog(blogId)));
    }

    @GetMapping(EndpointConstants.OpenController.BlogData.BLOG_COUNT_INTERNAL_RESPONSE)
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = BlogCountResponse.class)
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
            }
    )
    public ResponseEntity<BlogCountResponse> countBlogInternalResponse(@RequestParam String userId) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(blogService.countBlog(userId)));
    }

    @GetMapping(EndpointConstants.OpenController.LikeData.COUNT_LIKES)
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = LikeResponse.class)
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
            }
    )
    public ResponseEntity<CountResponse> getLikesCount(@RequestParam String destinationId) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(likeService.countLikes(destinationId)));
    }
}
