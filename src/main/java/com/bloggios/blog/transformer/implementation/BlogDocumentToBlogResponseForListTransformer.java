package com.bloggios.blog.transformer.implementation;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.dao.implementation.esimplementation.LikeDocumentDao;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.feign.implementation.ProfileInternalResponseFeignCall;
import com.bloggios.blog.payload.response.BlogResponseForList;
import com.bloggios.blog.payload.response.ProfileInternalResponse;
import com.bloggios.blog.transformer.Transform;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.transformer.implementation
 * Created_on - June 07 - 2024
 * Created_at - 18:42
 */

@Component
public class BlogDocumentToBlogResponseForListTransformer implements Transform<BlogResponseForList, BlogDocument> {

    private final ModelMapper modelMapper;
    private final ProfileInternalResponseFeignCall profileInternalResponseFeignCall;
    private final LikeDocumentDao likeDocumentDao;

    public BlogDocumentToBlogResponseForListTransformer(
            ModelMapper modelMapper,
            ProfileInternalResponseFeignCall profileInternalResponseFeignCall,
            LikeDocumentDao likeDocumentDao
    ) {
        this.modelMapper = modelMapper;
        this.profileInternalResponseFeignCall = profileInternalResponseFeignCall;
        this.likeDocumentDao = likeDocumentDao;
    }

    @Override
    public BlogResponseForList transform(BlogDocument blogDocument) {
        CompletableFuture<ProfileInternalResponse> profileInternalResponseCompletableFuture = CompletableFuture.supplyAsync(() -> profileInternalResponseFeignCall.callFeign(blogDocument.getUserId())
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.PROFILE_DATA_NOT_FOUND_FEIGN)));
        CompletableFuture<Long> likeCountCompletableFuture = CompletableFuture.supplyAsync(() -> likeDocumentDao.countLikeDocumentByDestinationId(blogDocument.getBlogId()));
        CompletableFuture.allOf(profileInternalResponseCompletableFuture, likeCountCompletableFuture);
        ProfileInternalResponse profileInternalResponse = profileInternalResponseCompletableFuture.join();
        BlogResponseForList blogResponseList = modelMapper.map(blogDocument, BlogResponseForList.class);
        blogResponseList.setName(profileInternalResponse.getName());
        blogResponseList.setEmail(profileInternalResponse.getEmail());
        blogResponseList.setProfileTag(profileInternalResponse.getProfileTag());
        blogResponseList.setProfileImage(profileInternalResponse.getProfileImage());
        blogResponseList.setBadge(profileInternalResponse.isBadge());
        blogResponseList.setLikes(likeCountCompletableFuture.join());
        return blogResponseList;
    }
}
