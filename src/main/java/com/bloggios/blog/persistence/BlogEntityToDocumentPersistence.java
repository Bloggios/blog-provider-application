package com.bloggios.blog.persistence;

import com.bloggios.blog.dao.implementation.esimplementation.BlogDocumentDao;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.blog.document.embeddable.ImageLinksNested;
import com.bloggios.blog.document.embeddable.TopicsNested;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.modal.BlogEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.persistence
 * Created_on - June 01 - 2024
 * Created_at - 21:24
 */

@Component
public class BlogEntityToDocumentPersistence {

    private final ModelMapper modelMapper;
    private final BlogDocumentDao blogDocumentDao;

    public BlogEntityToDocumentPersistence(ModelMapper modelMapper, BlogDocumentDao blogDocumentDao) {
        this.modelMapper = modelMapper;
        this.blogDocumentDao = blogDocumentDao;
    }

    public BlogDocument persist(BlogEntity blogEntity, DaoStatus daoStatus) {
        BlogDocument blogDocument = BlogDocument
                .builder()
                .blogId(blogEntity.getBlogId())
                .title(blogEntity.getTitle())
                .detailsHtml(blogEntity.getDetailsHtml())
                .delta(blogEntity.getDelta())
                .detailsText(blogEntity.getDetailsText())
                .userId(blogEntity.getUserId())
                .version(blogEntity.getVersion())
                .apiVersion(blogEntity.getApiVersion())
                .dateCreated(blogEntity.getDateCreated())
                .dateUpdated(blogEntity.getDateUpdated())
                .scheduledOn(blogEntity.getScheduledOn())
                .remoteAddress(blogEntity.getRemoteAddress())
                .imageLinks(getImageLinksNested(blogEntity))
                .chapterId(Objects.nonNull(blogEntity.getChapter()) ? blogEntity.getChapter().getChapterId() : null)
                .featureStatus(blogEntity.getFeatureStatus())
                .topics(getTopicsNested(blogEntity))
                .coverImage(blogEntity.getCoverImage())
                .build();
        return blogDocumentDao.initOperation(daoStatus, blogDocument);
    }

    private List<ImageLinksNested> getImageLinksNested(BlogEntity blogEntity) {
        if (CollectionUtils.isEmpty(blogEntity.getImageLinks())) return null;
        return blogEntity
                .getImageLinks()
                .stream()
                .map(e -> modelMapper.map(e, ImageLinksNested.class))
                .toList();
    }

    private List<TopicsNested> getTopicsNested(BlogEntity blogEntity) {
        if (CollectionUtils.isEmpty(blogEntity.getTopics())) return null;
        return blogEntity
                .getTopics()
                .stream()
                .map(e -> modelMapper.map(e, TopicsNested.class))
                .toList();
    }
}
