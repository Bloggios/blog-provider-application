package com.bloggios.blog.dao.repository.elasticsearch;

import com.bloggios.blog.document.ChapterDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.repository.elasticsearch
 * Created_on - June 01 - 2024
 * Created_at - 20:20
 */

public interface ChapterDocumentRepository extends ElasticsearchRepository<ChapterDocument, String> {
}
