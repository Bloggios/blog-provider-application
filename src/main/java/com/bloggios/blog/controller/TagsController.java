package com.bloggios.blog.controller;

import com.bloggios.blog.constants.EndpointConstants;
import com.bloggios.blog.payload.TagYmlDataProvider;
import com.bloggios.blog.ymlparser.TagDataParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.controller
 * Created_on - May 29 - 2024
 * Created_at - 20:47
 */

@RestController
@RequestMapping(EndpointConstants.TagsController.BASE_PATH)
public class TagsController {

    @GetMapping
    public String getData() {
    }

}
