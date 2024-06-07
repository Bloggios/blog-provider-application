package com.bloggios.blog.transformer.implementation;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.feign.implementation.ProfileInternalResponseFeignCall;
import com.bloggios.blog.payload.response.BlogResponseForList;
import com.bloggios.blog.payload.response.ProfileInternalResponse;
import com.bloggios.blog.transformer.Transform;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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

    public BlogDocumentToBlogResponseForListTransformer(ModelMapper modelMapper, ProfileInternalResponseFeignCall profileInternalResponseFeignCall) {
        this.modelMapper = modelMapper;
        this.profileInternalResponseFeignCall = profileInternalResponseFeignCall;
    }

    @Override
    public BlogResponseForList transform(BlogDocument blogDocument) {
        ProfileInternalResponse profileInternalResponse = profileInternalResponseFeignCall.callFeign(blogDocument.getUserId())
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.PROFILE_DATA_NOT_FOUND_FEIGN));
        BlogResponseForList blogResponseList = modelMapper.map(blogDocument, BlogResponseForList.class);
        blogResponseList.setName(profileInternalResponse.getName());
        blogResponseList.setEmail(profileInternalResponse.getEmail());
        blogResponseList.setProfileTag(profileInternalResponse.getProfileTag());
        blogResponseList.setProfileImage(profileInternalResponse.getProfileImage());
        blogResponseList.setBadge(profileInternalResponse.isBadge());
        return blogResponseList;
    }
}
