package com.bloggios.blog.dao.repository.elasticsearch;

import com.bloggios.blog.document.LikeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.repository.elasticsearch
 * Created_on - June 09 - 2024
 * Created_at - 21:46
 */

public interface LikeDocumentRepository extends ElasticsearchRepository<LikeDocument, String> {

    Optional<LikeDocument> findByDestinationIdAndUserId(String destinationId, String userId);
}
