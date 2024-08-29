package com.bloggios.blog.validator.implementation.businessvalidator;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.SchedulerDataDao;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.modal.SchedulerData;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.validator.BusinessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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
@RequiredArgsConstructor
public class SchedulerTimeValidator implements BusinessValidator<BlogRequest> {

    private final SchedulerDataDao schedulerDataDao;

    @Override
    public void validate(BlogRequest blogRequest) {
        Long milliseconds = blogRequest.getMilliseconds();
        if (Objects.isNull(milliseconds)) return;
        long twentyFourHoursInMillis = 24 * 60 * 60 * 1000;
        if (milliseconds > twentyFourHoursInMillis) {
            throw new BadRequestException(DataErrorCodes.BLOG_CANNOT_BE_SCHEDULED_FOR_MORE_THAN_ONE_DAY);
        }
        List<SchedulerData> byUserId = schedulerDataDao.findByUserId(blogRequest.getAuthenticatedUser().getUserId());
        if (byUserId.size() > 5) {
            throw new BadRequestException(DataErrorCodes.BLOG_SCHEDULE_LIMIT_EXCEED);
        }
    }
}
