package com.bloggios.blog.transformer.implementation;

import com.bloggios.blog.document.ChapterDocument;
import com.bloggios.blog.payload.response.ChapterResponse;
import com.bloggios.blog.transformer.Transform;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.transformer.implementation
 * Created_on - June 08 - 2024
 * Created_at - 13:46
 */

@Component
public class ChapterDocumentToResponseTransformer implements Transform<ChapterResponse, ChapterDocument> {

    @Override
    public ChapterResponse transform(ChapterDocument chapterDocument) {
        return ChapterResponse
                .builder()
                .chapterId(chapterDocument.getChapterId())
                .chapterName(chapterDocument.getChapterName())
                .coverImage(chapterDocument.getCoverImage())
                .build();
    }
}
