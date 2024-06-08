package com.bloggios.blog.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.constants.EndpointConstants;
import com.bloggios.blog.payload.request.ChapterRequest;
import com.bloggios.blog.payload.response.ModuleResponse;
import com.bloggios.blog.service.ChapterService;
import com.bloggios.blog.utils.AsyncUtils;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.controller
 * Created_on - June 02 - 2024
 * Created_at - 12:05
 */

@RestController
@RequestMapping(EndpointConstants.ChapterController.BASE_PATH)
public class ChapterController {

    private final ChapterService chapterService;

    public ChapterController(
            ChapterService chapterService
    ) {
        this.chapterService = chapterService;
    }

    @PostMapping
    public ResponseEntity<ModuleResponse> addChapter(
            @RequestParam String chapterName,
            @RequestPart(required = false) MultipartFile coverImage,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam(required = false) List<String> topics
    ) {
        ChapterRequest chapterRequest = ChapterRequest
                .builder()
                .chapterName(chapterName)
                .authenticatedUser(authenticatedUser)
                .topics(topics)
                .coverImage(coverImage)
                .build();
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(
                chapterService.addChapter(chapterRequest)
        ));
    }

    @GetMapping
    public ResponseEntity<ListResponse> getUserChapters(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(chapterService.getUserChapters(authenticatedUser)));
    }
}
