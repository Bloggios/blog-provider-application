package com.bloggios.blog.payload.record;

import com.bloggios.blog.modal.embeddable.ImageLinksEntity;

import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.payload.record
 * Created_on - June 01 - 2024
 * Created_at - 19:31
 */

public record BlogImagesAndHtmlRecord(
        List<ImageLinksEntity> imageLinks,
        String modifiedHtml
) {
}
