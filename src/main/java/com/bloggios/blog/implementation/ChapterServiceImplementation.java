package com.bloggios.blog.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.blog.constants.ResponseMessageConstants;
import com.bloggios.blog.dao.implementation.esimplementation.ChapterDocumentDao;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.ChapterEntityDao;
import com.bloggios.blog.document.ChapterDocument;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.modal.ChapterEntity;
import com.bloggios.blog.payload.request.ChapterRequest;
import com.bloggios.blog.payload.response.ChapterResponse;
import com.bloggios.blog.payload.response.ModuleResponse;
import com.bloggios.blog.persistence.ChapterEntityToDocumentPersistence;
import com.bloggios.blog.processor.implementation.CoverImageLinkProcessor;
import com.bloggios.blog.service.ChapterService;
import com.bloggios.blog.transformer.implementation.ChapterDocumentToResponseTransformer;
import com.bloggios.blog.transformer.implementation.ChapterRequestToEntityTransformer;
import com.bloggios.blog.validator.implementation.exhibitor.ChapterRequestExhibitor;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.implementation
 * Created_on - June 02 - 2024
 * Created_at - 12:08
 */

@Component
public class ChapterServiceImplementation implements ChapterService {

    private static final Logger logger = LoggerFactory.getLogger(ChapterServiceImplementation.class);

    private final ChapterRequestExhibitor chapterRequestExhibitor;
    private final CoverImageLinkProcessor coverImageLinkProcessor;
    private final ChapterRequestToEntityTransformer chapterRequestToEntityTransformer;
    private final ChapterEntityDao chapterEntityDao;
    private final ChapterEntityToDocumentPersistence chapterEntityToDocumentPersistence;
    private final ChapterDocumentDao chapterDocumentDao;
    private final ChapterDocumentToResponseTransformer chapterDocumentToResponseTransformer;

    public ChapterServiceImplementation(ChapterRequestExhibitor chapterRequestExhibitor, CoverImageLinkProcessor coverImageLinkProcessor, ChapterRequestToEntityTransformer chapterRequestToEntityTransformer, ChapterEntityDao chapterEntityDao, ChapterEntityToDocumentPersistence chapterEntityToDocumentPersistence, ChapterDocumentDao chapterDocumentDao, ChapterDocumentToResponseTransformer chapterDocumentToResponseTransformer) {
        this.chapterRequestExhibitor = chapterRequestExhibitor;
        this.coverImageLinkProcessor = coverImageLinkProcessor;
        this.chapterRequestToEntityTransformer = chapterRequestToEntityTransformer;
        this.chapterEntityDao = chapterEntityDao;
        this.chapterEntityToDocumentPersistence = chapterEntityToDocumentPersistence;
        this.chapterDocumentDao = chapterDocumentDao;
        this.chapterDocumentToResponseTransformer = chapterDocumentToResponseTransformer;
    }

    @Override
    public CompletableFuture<ModuleResponse> addChapter(ChapterRequest chapterRequest) {
        long startTime = System.currentTimeMillis();
        chapterRequestExhibitor.validate(chapterRequest);
        String coverImage = coverImageLinkProcessor.process(chapterRequest);
        ChapterEntity chapterEntity = chapterRequestToEntityTransformer.transform(chapterRequest, coverImage);
        ChapterEntity chapterEntityResponse = chapterEntityDao.initOperation(DaoStatus.CREATE, chapterEntity);
        ChapterDocument chapterDocument = chapterEntityToDocumentPersistence.persist(chapterEntityResponse, DaoStatus.CREATE);
        logger.info("Execution Time (Add Chapter) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ModuleResponse
                        .builder()
                        .message(ResponseMessageConstants.CHAPTER_CREATED)
                        .userId(chapterDocument.getUserId())
                        .id(chapterDocument.getChapterId())
                        .build()
        );
    }

    @Override
    public CompletableFuture<ListResponse> getUserChapters(AuthenticatedUser authenticatedUser) {
        List<ChapterDocument> chapterDocuments = chapterDocumentDao.findByUserId(authenticatedUser.getUserId());
        if (CollectionUtils.isEmpty(chapterDocuments)) {
            return CompletableFuture.completedFuture(
                    ListResponse
                            .builder()
                            .page(0)
                            .size(0)
                            .totalRecordsCount(0)
                            .object(Collections.emptyList())
                            .build()
            );
        }
        List<ChapterResponse> chapterResponses = chapterDocuments
                .stream()
                .map(chapterDocumentToResponseTransformer::transform)
                .toList();
        return CompletableFuture.completedFuture(
                ListResponse
                        .builder()
                        .page(0)
                        .size(chapterResponses.size())
                        .totalRecordsCount(chapterResponses.size())
                        .object(chapterResponses)
                        .build()
        );
    }
}
