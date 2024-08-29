package com.bloggios.blog.scheduler;

import com.bloggios.blog.dao.implementation.pgsqlimplementation.SchedulerDataDao;
import com.bloggios.blog.modal.SchedulerData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.scheduler
 * Created_on - August 29 - 2024
 * Created_at - 19:03
 */

@Component
@Slf4j
public class SchedulerValidator {

    private final SchedulerDataDao schedulerDataDao;

    public SchedulerValidator(
            SchedulerDataDao schedulerDataDao
    ) {
        this.schedulerDataDao = schedulerDataDao;
    }

    public void validate(SchedulerData schedulerData) {
        if (schedulerData.getScheduleDate().compareTo(new Date()) <= 0) {
            return;
        }

        if (schedulerData.isSchedulingDone()) {
            schedulerDataDao.deleteByEntity(schedulerData);
            log.warn("""
                    Scheduler Entry deleted for below details
                    Scheduler Id: {}
                    Destination Id: {}
                    Task For: {}
                    Scheduled Completed On: {}
                    """,
                    schedulerData.getSchedulerId(),
                    schedulerData.getDestId(),
                    schedulerData.getScheduledTaskType().toString(),
                    schedulerData.getScheduleCompletedOn());
        }
    }
}
