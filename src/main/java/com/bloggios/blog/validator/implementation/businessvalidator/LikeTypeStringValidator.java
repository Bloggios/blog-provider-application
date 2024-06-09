package com.bloggios.blog.validator.implementation.businessvalidator;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.enums.LikeType;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.validator.BusinessValidator;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.businessvalidator
 * Created_on - June 09 - 2024
 * Created_at - 21:51
 */

@Component
public class LikeTypeStringValidator implements BusinessValidator<String> {

    @Override
    public void validate(String likeFor) {
        try {
            LikeType.valueOf(likeFor);
        } catch (IllegalArgumentException ignored) {
            throw new BadRequestException(DataErrorCodes.LIKE_FOR_IS_INVALID);
        }
    }
}
