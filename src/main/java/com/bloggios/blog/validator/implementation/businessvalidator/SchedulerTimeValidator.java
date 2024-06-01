package com.bloggios.blog.validator.implementation.businessvalidator;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.validator.BusinessValidator;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.businessvalidator
 * Created_on - June 01 - 2024
 * Created_at - 16:49
 */

@Component
public class SchedulerTimeValidator implements BusinessValidator<BlogRequest> {

    @Override
    public void validate(BlogRequest blogRequest) {
        Long milliseconds = blogRequest.getMilliseconds();
        if (blogRequest.isDraft() && Objects.nonNull(milliseconds)) {
            throw new BadRequestException(DataErrorCodes.DRAFT_BLOG_CANNOT_SCHEDULED);
        }
        Calendar calendar = Calendar.getInstance();
        long twentyFourHoursInMillis = 24 * 60 * 60 * 1000;
        if (milliseconds > twentyFourHoursInMillis) {
            throw new BadRequestException(DataErrorCodes.BLOG_CANNOT_BE_SCHEDULED_FOR_MORE_THAN_ONE_DAY);
        }
    }
}
