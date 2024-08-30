package com.bloggios.blog.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.constants.EndpointConstants;
import com.bloggios.blog.payload.response.ExceptionResponse;
import com.bloggios.blog.service.SchedulerService;
import com.bloggios.blog.utils.AsyncUtils;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.controller
 * Created_on - August 30 - 2024
 * Created_at - 00:44
 */

@RestController
@RequestMapping(EndpointConstants.SchedulerController.BASE_PATH)
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;

    @GetMapping
    @Operation(
            requestBody = @RequestBody(
                    required = false
            ),
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
    public ResponseEntity<ListResponse> getUserScheduledBlogs(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(schedulerService.getUserScheduler(authenticatedUser)));
    }
}
