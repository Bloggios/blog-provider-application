package com.bloggios.blog.dao.repository.postgres;

import com.bloggios.blog.document.ChapterDocument;
import com.bloggios.blog.modal.ChapterEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.repository.elasticsearch
 * Created_on - June 01 - 2024
 * Created_at - 20:20
 */

public interface ChapterEntityRepository extends JpaRepository<ChapterEntity, String> {
}
