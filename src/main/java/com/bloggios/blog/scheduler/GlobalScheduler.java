package com.bloggios.blog.scheduler;

import com.bloggios.blog.scheduler.executor.TwoMinutesScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.scheduler
 * Created_on - August 29 - 2024
 * Created_at - 18:34
 */

@Component
@Slf4j
public class GlobalScheduler {

    private final TwoMinutesScheduler twoMinutesScheduler;

    public GlobalScheduler(
            TwoMinutesScheduler twoMinutesScheduler
    ) {
        this.twoMinutesScheduler = twoMinutesScheduler;
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void everyMinuteScheduler() {
        long startTime = System.currentTimeMillis();
        String id = UUID.randomUUID().toString();
        log.info("Executing every two(2) minutes scheduler with Id: {}", id);
        twoMinutesScheduler.initOperation();
        log.info("""
                Every Two(2) Minute Scheduling Completed for Id: {}
                Time Taken: {}
                """,
                id,
                System.currentTimeMillis() - startTime);
    }
}
