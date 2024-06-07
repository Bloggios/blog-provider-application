package com.bloggios.blog.feign;

import com.bloggios.blog.constants.EndpointConstants;
import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.payload.response.ProfileInternalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.feign
 * Created_on - May 24 - 2024
 * Created_at - 12:25
 */

@FeignClient(
        name = ServiceConstants.USER_PROVIDER_APPLICATION,
        url = EnvironmentConstants.BASE_PATH
)
public interface UserProviderApplicationFeign {

    @GetMapping(EndpointConstants.FeignClient.PROFILE_INTERNAL_RESPONSE)
    ResponseEntity<ProfileInternalResponse> profileInternalResponse(@PathVariable String userId);
}
