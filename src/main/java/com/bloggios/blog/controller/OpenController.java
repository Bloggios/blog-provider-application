package com.bloggios.blog.controller;

import com.bloggios.blog.constants.EndpointConstants;
import com.bloggios.blog.service.BlogService;
import com.bloggios.blog.utils.AsyncUtils;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
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

    @GetMapping(EndpointConstants.OpenController.BLOGS_LIST)
    public ResponseEntity<ListResponse> blogsList(
            @RequestParam Integer page,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String topic
    ) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(blogService.unauthBlogList(
                page, userId, topic
        )));
    }
}
