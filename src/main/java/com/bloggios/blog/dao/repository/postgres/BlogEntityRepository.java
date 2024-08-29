package com.bloggios.blog.dao.repository.postgres;

import com.bloggios.blog.modal.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.repository.elasticsearch
 * Created_on - June 01 - 2024
 * Created_at - 20:09
 */

public interface BlogEntityRepository extends JpaRepository<BlogEntity, String> {
}
