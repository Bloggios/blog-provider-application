package com.bloggios.blog.scheduler;

import com.bloggios.blog.modal.SchedulerData;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.scheduler
 * Created_on - August 29 - 2024
 * Created_at - 18:28
 */

@FunctionalInterface
public interface ExecuteScheduler {

    void execute(SchedulerData schedulerData);
}
