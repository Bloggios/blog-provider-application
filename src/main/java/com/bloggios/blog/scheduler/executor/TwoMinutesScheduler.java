package com.bloggios.blog.scheduler.executor;

import com.bloggios.blog.dao.implementation.pgsqlimplementation.SchedulerDataDao;
import com.bloggios.blog.modal.SchedulerData;
import com.bloggios.blog.scheduler.TimeScheduler;
import com.bloggios.blog.scheduler.implementation.BlogSchedulerImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.scheduler.executor
 * Created_on - August 29 - 2024
 * Created_at - 18:36
 */

@Component
@Slf4j
public class TwoMinutesScheduler implements TimeScheduler {

    private final SchedulerDataDao schedulerDataDao;
    private final BlogSchedulerImplementation blogSchedulerImplementation;

    public TwoMinutesScheduler(
            SchedulerDataDao schedulerDataDao,
            BlogSchedulerImplementation blogSchedulerImplementation
    ) {
        this.schedulerDataDao = schedulerDataDao;
        this.blogSchedulerImplementation = blogSchedulerImplementation;
    }

    @Override
    public void initOperation() {
        List<SchedulerData> overduePendingSchedulingData = schedulerDataDao.getOverduePendingSchedulingData();
        if (overduePendingSchedulingData.isEmpty()) return;
        overduePendingSchedulingData
                .forEach(schedulerData -> {
                    switch (schedulerData.getScheduledTaskType()) {
                        case BLOG_SCHEDULE -> blogSchedulerImplementation.execute(schedulerData);
                    }
                });
    }
}
