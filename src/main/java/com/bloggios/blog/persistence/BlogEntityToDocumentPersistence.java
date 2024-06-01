package com.bloggios.blog.persistence;

import com.bloggios.blog.dao.implementation.esimplementation.BlogDocumentDao;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.modal.BlogEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
        BlogDocument blogDocument = modelMapper.map(blogEntity, BlogDocument.class);
        String chapterId = blogEntity.getChapter().getChapterId();
        blogDocument.setChapterId(chapterId);
        return blogDocumentDao.initOperation(daoStatus, blogDocument);
    }
}
