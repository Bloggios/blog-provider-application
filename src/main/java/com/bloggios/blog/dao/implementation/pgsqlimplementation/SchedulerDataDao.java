package com.bloggios.blog.dao.implementation.pgsqlimplementation;

import com.bloggios.blog.dao.PgAbstractDao;
import com.bloggios.blog.dao.repository.postgres.SchedulerDataRepository;
import com.bloggios.blog.modal.SchedulerData;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.implementation.pgsqlimplementation
 * Created_on - August 29 - 2024
 * Created_at - 18:45
 */

@Component
public class SchedulerDataDao extends PgAbstractDao<SchedulerData, SchedulerDataRepository> {

    protected SchedulerDataDao(SchedulerDataRepository repository) {
        super(repository);
    }

    public List<SchedulerData> getOverduePendingSchedulingData() {
        return repository.findAllByIsSchedulingDoneTrueAndScheduleDateLessThanEqual(new Date());
    }

    public void deleteByEntity(SchedulerData schedulerData) {
        repository.delete(schedulerData);
    }
}
