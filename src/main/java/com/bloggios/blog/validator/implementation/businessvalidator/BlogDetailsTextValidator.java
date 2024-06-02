package com.bloggios.blog.validator.implementation.businessvalidator;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.constants.ResponseErrorMessageConstants;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.validator.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.businessvalidator
 * Created_on - June 01 - 2024
 * Created_at - 16:24
 */

@Component
public class BlogDetailsTextValidator implements BusinessValidator<BlogRequest> {

    @Override
    public void validate(BlogRequest blogRequest) {
        AuthenticatedUser authenticatedUser = blogRequest.getAuthenticatedUser();
        if (ObjectUtils.isEmpty(blogRequest.getDetailsText()))
            throw new BadRequestException(DataErrorCodes.DETAILS_NOT_PRESENT);
        if (authenticatedUser.isBadge()) {
            if (blogRequest.getDetailsText().length() > 10000) {
                throw new BadRequestException(DataErrorCodes.BLOG_BODY_LIMIT_EXCEED,
                        String.format(ResponseErrorMessageConstants.BLOG_BODY_LIMIT_EXCEED, "10000"));
            }
        } else {
            if (blogRequest.getDetailsText().length() > 5000) {
                throw new BadRequestException(DataErrorCodes.BLOG_BODY_LIMIT_EXCEED,
                        String.format(ResponseErrorMessageConstants.BLOG_BODY_LIMIT_EXCEED, "5000"));
            }
        }
    }
}
