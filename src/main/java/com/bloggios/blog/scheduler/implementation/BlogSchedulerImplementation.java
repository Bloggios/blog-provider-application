package com.bloggios.blog.scheduler.implementation;

import com.bloggios.blog.dao.implementation.pgsqlimplementation.BlogEntityDao;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.SchedulerDataDao;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.enums.FeatureStatus;
import com.bloggios.blog.modal.BlogEntity;
import com.bloggios.blog.modal.SchedulerData;
import com.bloggios.blog.persistence.BlogEntityToDocumentPersistence;
import com.bloggios.blog.scheduler.ExecuteScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.scheduler.implementation
 * Created_on - August 29 - 2024
 * Created_at - 18:54
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class BlogSchedulerImplementation implements ExecuteScheduler {

    private final SchedulerDataDao schedulerDataDao;
    private final BlogEntityDao blogEntityDao;
    private final BlogEntityToDocumentPersistence blogEntityToDocumentPersistence;

    @Override
    @Transactional
    public void execute(SchedulerData schedulerData) {
        Optional<BlogEntity> optionalBlogEntity = blogEntityDao.findById(schedulerData.getDestId());
        if (optionalBlogEntity.isEmpty()) return;
        BlogEntity blogEntity = optionalBlogEntity.get();
        blogEntity.setFeatureStatus(FeatureStatus.VISIBLE);
        blogEntity.setVersion(UUID.randomUUID().toString());
        BlogEntity response = blogEntityDao.initOperation(DaoStatus.UPDATE, blogEntity);
        BlogDocument blogDocument = blogEntityToDocumentPersistence.persist(response, DaoStatus.UPDATE);
        schedulerDataDao.deleteByEntity(schedulerData);
        log.info("""
                Scheduler Complete for Blog with below Details
                Blog Id: {}
                Current Feature: {}
                """,
                blogDocument.getBlogId(),
                blogDocument.getFeatureStatus());
    }
}
