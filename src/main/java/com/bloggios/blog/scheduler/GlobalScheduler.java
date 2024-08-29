package com.bloggios.blog.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.scheduler
 * Created_on - August 29 - 2024
 * Created_at - 18:34
 */

@Component
public class GlobalScheduler {

    @Scheduled(cron = "0 */2 * * * ?")
    public void everyMinuteScheduler() {

    }
}
