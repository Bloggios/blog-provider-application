package com.bloggios.blog.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.constants.BeanConstants;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.SchedulerDataDao;
import com.bloggios.blog.modal.SchedulerData;
import com.bloggios.blog.service.SchedulerService;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.implementation
 * Created_on - August 30 - 2024
 * Created_at - 00:50
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerServiceImplementation implements SchedulerService {

    private final SchedulerDataDao schedulerDataDao;

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ListResponse> getUserScheduler(AuthenticatedUser authenticatedUser) {
        long startTime = System.currentTimeMillis();
        List<SchedulerData> byUserId = schedulerDataDao.findByUserId(authenticatedUser.getUserId());
        ListResponse listResponse = ListResponse
                .builder()
                .page(0)
                .size(byUserId.size())
                .object(byUserId)
                .totalRecordsCount(byUserId.size())
                .build();
        log.info("Execution time (Add Blog) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(listResponse);
    }
}
