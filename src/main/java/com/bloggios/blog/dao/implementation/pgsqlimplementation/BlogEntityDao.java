package com.bloggios.blog.dao.implementation.pgsqlimplementation;

import com.bloggios.blog.dao.PgAbstractDao;
import com.bloggios.blog.dao.repository.postgres.BlogEntityRepository;
import com.bloggios.blog.modal.BlogEntity;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.implementation.esimplementation
 * Created_on - June 01 - 2024
 * Created_at - 20:25
 */

@Component
public class BlogEntityDao extends PgAbstractDao<BlogEntity, BlogEntityRepository> {

    protected BlogEntityDao(BlogEntityRepository repository) {
        super(repository);
    }
}
