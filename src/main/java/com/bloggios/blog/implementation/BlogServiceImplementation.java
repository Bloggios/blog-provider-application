package com.bloggios.blog.implementation;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.constants.ResponseMessageConstants;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.BlogEntityDao;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.ChapterEntityDao;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.modal.BlogEntity;
import com.bloggios.blog.modal.ChapterEntity;
import com.bloggios.blog.payload.record.BlogImagesAndHtmlRecord;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.payload.response.ModuleResponse;
import com.bloggios.blog.persistence.BlogEntityToDocumentPersistence;
import com.bloggios.blog.processor.implementation.CoverImageLinkProcessor;
import com.bloggios.blog.processor.implementation.GenerateImageLinksWithModifiedHtml;
import com.bloggios.blog.processor.implementation.HtmlDataManipulation;
import com.bloggios.blog.service.BlogService;
import com.bloggios.blog.transformer.implementation.BlogRequestToBlogEntityTransformer;
import com.bloggios.blog.validator.implementation.exhibitor.BlogRequestExhibitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.implementation
 * Created_on - June 01 - 2024
 * Created_at - 15:08
 */

@Service
public class BlogServiceImplementation implements BlogService {

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImplementation.class);

    private final BlogRequestExhibitor blogRequestExhibitor;
    private final GenerateImageLinksWithModifiedHtml generateImageLinksWithModifiedHtml;
    private final CoverImageLinkProcessor coverImageLinkProcessor;
    private final ChapterEntityDao chapterEntityDao;
    private final BlogRequestToBlogEntityTransformer blogRequestToBlogEntityTransformer;
    private final BlogEntityDao blogEntityDao;
    private final BlogEntityToDocumentPersistence blogEntityToDocumentPersistence;
    private final HtmlDataManipulation htmlDataManipulation;

    public BlogServiceImplementation(
            BlogRequestExhibitor blogRequestExhibitor,
            GenerateImageLinksWithModifiedHtml generateImageLinksWithModifiedHtml,
            CoverImageLinkProcessor coverImageLinkProcessor,
            ChapterEntityDao chapterEntityDao,
            BlogRequestToBlogEntityTransformer blogRequestToBlogEntityTransformer,
            BlogEntityDao blogEntityDao,
            BlogEntityToDocumentPersistence blogEntityToDocumentPersistence,
            HtmlDataManipulation htmlDataManipulation) {
        this.blogRequestExhibitor = blogRequestExhibitor;
        this.generateImageLinksWithModifiedHtml = generateImageLinksWithModifiedHtml;
        this.coverImageLinkProcessor = coverImageLinkProcessor;
        this.chapterEntityDao = chapterEntityDao;
        this.blogRequestToBlogEntityTransformer = blogRequestToBlogEntityTransformer;
        this.blogEntityDao = blogEntityDao;
        this.blogEntityToDocumentPersistence = blogEntityToDocumentPersistence;
        this.htmlDataManipulation = htmlDataManipulation;
    }

    @Override
    public CompletableFuture<ModuleResponse> addBlog(BlogRequest blogRequest) {
        long startTime = System.currentTimeMillis();
        blogRequestExhibitor.validate(blogRequest);
        ChapterEntity chapterEntity = null;
        if (StringUtils.hasText(blogRequest.getChapterId())) {
            chapterEntity = chapterEntityDao.findByChapterId(blogRequest.getChapterId())
                    .orElseThrow(() -> new BadRequestException(DataErrorCodes.CHAPTER_ID_NOT_FOUND));
        }
        BlogImagesAndHtmlRecord imagesAndHtmlRecord = generateImageLinksWithModifiedHtml.process(blogRequest);
        String coverImageLink = coverImageLinkProcessor.process(blogRequest);
        String finalHtml = htmlDataManipulation.process(imagesAndHtmlRecord.modifiedHtml());
        BlogEntity blogEntity = blogRequestToBlogEntityTransformer.transform(blogRequest, imagesAndHtmlRecord, coverImageLink, chapterEntity, finalHtml);
        BlogEntity blogEntityResponse = blogEntityDao.initOperation(DaoStatus.CREATE, blogEntity);
        BlogDocument blogDocument = blogEntityToDocumentPersistence.persist(blogEntityResponse, DaoStatus.CREATE);
        logger.info("Execution time (Add Blog) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ModuleResponse
                        .builder()
                        .userId(blogDocument.getUserId())
                        .message(ResponseMessageConstants.BLOG_CREATED)
                        .id(blogDocument.getBlogId())
                        .build()
        );
    }
}
