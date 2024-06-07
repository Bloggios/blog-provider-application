package com.bloggios.blog.feign.implementation;

import com.bloggios.blog.feign.UserProviderApplicationFeign;
import com.bloggios.blog.payload.response.ProfileInternalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.feign.implementation
 * Created_on - June 07 - 2024
 * Created_at - 18:56
 */

@Component
public class ProfileInternalResponseFeignCall {

    private final UserProviderApplicationFeign userProviderApplicationFeign;

    public ProfileInternalResponseFeignCall(UserProviderApplicationFeign userProviderApplicationFeign) {
        this.userProviderApplicationFeign = userProviderApplicationFeign;
    }

    public Optional<ProfileInternalResponse> callFeign(String userId) {
        ResponseEntity<ProfileInternalResponse> responseEntity = userProviderApplicationFeign.profileInternalResponse(userId);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(responseEntity.getBody());
        }
        return Optional.empty();
    }
}
