package com.bloggios.blog.processor.implementation;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.SchedulerDataDao;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.enums.ScheduledTaskType;
import com.bloggios.blog.modal.BlogEntity;
import com.bloggios.blog.modal.SchedulerData;
import com.bloggios.blog.payload.request.BlogRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.processor.implementation
 * Created_on - August 29 - 2024
 * Created_at - 19:27
 */

@Component
@Slf4j
public class AddBlogSchedulerProcessor {

    private final Environment environment;
    private final SchedulerDataDao schedulerDataDao;

    public AddBlogSchedulerProcessor(
            Environment environment,
            SchedulerDataDao schedulerDataDao
    ) {
        this.environment = environment;
        this.schedulerDataDao = schedulerDataDao;
    }

    public void process(BlogRequest blogRequest, BlogEntity blogEntity) {
        Date currentDate = Calendar.getInstance().getTime();
        Date scheduledDate = new Date(currentDate.getTime() + blogRequest.getMilliseconds());
        SchedulerData transform = transform(scheduledDate, blogEntity);
        SchedulerData schedulerData = schedulerDataDao.initOperation(DaoStatus.CREATE, transform);
        log.info("""
                Scheduler Created for Blog with below Details
                Scheduler Id: {}
                Blog Id: {}
                """,
                schedulerData.getSchedulerId(),
                blogEntity.getBlogId());
    }

    private SchedulerData transform(Date scheduledDate, BlogEntity blogEntity) {
        return SchedulerData
                .builder()
                .schedulerId(UUID.randomUUID().toString())
                .dateCreated(new Date())
                .scheduleDate(scheduledDate)
                .destId(blogEntity.getBlogId())
                .apiVersion(environment.getProperty(EnvironmentConstants.APPLICATION_VERSION))
                .isSchedulingDone(false)
                .scheduledTaskType(ScheduledTaskType.BLOG_SCHEDULE)
                .build();
    }
}
