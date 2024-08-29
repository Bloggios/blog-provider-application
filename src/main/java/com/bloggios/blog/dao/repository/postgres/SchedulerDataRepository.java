package com.bloggios.blog.dao.repository.postgres;

import com.bloggios.blog.modal.SchedulerData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.repository.postgres
 * Created_on - August 29 - 2024
 * Created_at - 18:42
 */

public interface SchedulerDataRepository extends JpaRepository<SchedulerData, String> {

    List<SchedulerData> findAllByIsSchedulingDoneTrueAndScheduleDateLessThanEqual(Date currentDate);
}
