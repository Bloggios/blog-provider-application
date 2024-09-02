package com.bloggios.blog.transformer.implementation;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.dao.implementation.esimplementation.ChapterDocumentDao;
import com.bloggios.blog.dao.implementation.esimplementation.LikeDocumentDao;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.blog.document.ChapterDocument;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.feign.implementation.ProfileInternalResponseFeignCall;
import com.bloggios.blog.payload.response.BlogResponse;
import com.bloggios.blog.payload.response.ProfileInternalResponse;
import com.bloggios.blog.transformer.Transform;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.transformer.implementation
 * Created_on - June 07 - 2024
 * Created_at - 21:33
 */

@Component
@RequiredArgsConstructor
public class BlogDocumentToBlogResponseTransformer implements Transform<BlogResponse, BlogDocument> {

    private final ModelMapper modelMapper;
    private final ProfileInternalResponseFeignCall profileInternalResponseFeignCall;
    private final ChapterDocumentDao chapterDocumentDao;
    private final LikeDocumentDao likeDocumentDao;

    @Override
    public BlogResponse transform(BlogDocument blogDocument) {
        BlogResponse blogResponse = modelMapper.map(blogDocument, BlogResponse.class);
        CompletableFuture<ProfileInternalResponse> profileInternalResponseCompletableFuture = CompletableFuture.supplyAsync(() -> profileInternalResponseFeignCall.callFeign(blogDocument.getUserId())
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.PROFILE_DATA_NOT_FOUND_FEIGN)));
        CompletableFuture<Long> likeCountCompletableFuture = CompletableFuture.supplyAsync(() -> likeDocumentDao.countLikeDocumentByDestinationId(blogDocument.getBlogId()));
        CompletableFuture.allOf(profileInternalResponseCompletableFuture, likeCountCompletableFuture);
        ChapterDocument chapterDocument = null;
        if (StringUtils.hasText(blogDocument.getChapterId())) {
            chapterDocument = chapterDocumentDao.findById(blogDocument.getChapterId())
                    .orElseThrow(() -> new BadRequestException(DataErrorCodes.CHAPTER_ID_NOT_FOUND));
        }
        ProfileInternalResponse profileInternalResponse = profileInternalResponseCompletableFuture.join();
        blogResponse.setName(profileInternalResponse.getName());
        blogResponse.setEmail(profileInternalResponse.getEmail());
        blogResponse.setProfileTag(profileInternalResponse.getProfileTag());
        blogResponse.setProfileImage(profileInternalResponse.getProfileImage());
        blogResponse.setBadge(profileInternalResponse.isBadge());
        blogResponse.setChapterName(Objects.nonNull(chapterDocument) ? chapterDocument.getChapterName() : null);
        blogResponse.setLikes(likeCountCompletableFuture.join() != null ? likeCountCompletableFuture.join() : 0);
        return blogResponse;
    }
}
