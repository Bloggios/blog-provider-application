package com.bloggios.blog.persistence;

import com.bloggios.blog.dao.implementation.esimplementation.ChapterDocumentDao;
import com.bloggios.blog.document.ChapterDocument;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.modal.ChapterEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.persistence
 * Created_on - June 02 - 2024
 * Created_at - 12:37
 */

@Component
public class ChapterEntityToDocumentPersistence {

    private final ModelMapper modelMapper;
    private final ChapterDocumentDao chapterDocumentDao;

    public ChapterEntityToDocumentPersistence(ModelMapper modelMapper, ChapterDocumentDao chapterDocumentDao) {
        this.modelMapper = modelMapper;
        this.chapterDocumentDao = chapterDocumentDao;
    }

    public ChapterDocument persist(ChapterEntity chapterEntity, DaoStatus daoStatus) {
        ChapterDocument chapterDocument = modelMapper.map(chapterEntity, ChapterDocument.class);
        return chapterDocumentDao.initOperation(daoStatus, chapterDocument);
    }
}
