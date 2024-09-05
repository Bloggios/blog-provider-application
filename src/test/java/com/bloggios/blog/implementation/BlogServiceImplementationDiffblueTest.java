package com.bloggios.blog.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.dao.implementation.esimplementation.BlogDocumentDao;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.BlogEntityDao;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.ChapterEntityDao;
import com.bloggios.blog.document.BlogDocument;
import com.bloggios.blog.enums.DaoStatus;
import com.bloggios.blog.enums.FeatureStatus;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.feign.UserProviderApplicationFeign;
import com.bloggios.blog.feign.implementation.ProfileInternalResponseFeignCall;
import com.bloggios.blog.file.UploadFile;
import com.bloggios.blog.modal.BlogEntity;
import com.bloggios.blog.modal.ChapterEntity;
import com.bloggios.blog.payload.record.BlogImagesAndHtmlRecord;
import com.bloggios.blog.payload.request.BlogListRequest;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.payload.response.ModuleResponse;
import com.bloggios.blog.persistence.BlogEntityToDocumentPersistence;
import com.bloggios.blog.processor.implementation.AddBlogSchedulerProcessor;
import com.bloggios.blog.processor.implementation.AddImageLinksHtmlProcessor;
import com.bloggios.blog.processor.implementation.CoverImageLinkProcessor;
import com.bloggios.blog.processor.implementation.GenerateImageLinksWithModifiedHtml;
import com.bloggios.blog.processor.implementation.HtmlDataManipulation;
import com.bloggios.blog.processor.implementation.UploadImagesLinkProcessor;
import com.bloggios.blog.transformer.implementation.BlogDocumentToBlogResponseForListTransformer;
import com.bloggios.blog.transformer.implementation.BlogDocumentToBlogResponseTransformer;
import com.bloggios.blog.transformer.implementation.BlogListToListRequestTransformer;
import com.bloggios.blog.transformer.implementation.BlogRequestToBlogEntityTransformer;
import com.bloggios.blog.validator.implementation.businessvalidator.BlogDetailsTextValidator;
import com.bloggios.blog.validator.implementation.businessvalidator.BlogImagesValidator;
import com.bloggios.blog.validator.implementation.businessvalidator.CoverImageValidator;
import com.bloggios.blog.validator.implementation.businessvalidator.HtmlDetailsValidator;
import com.bloggios.blog.validator.implementation.businessvalidator.SchedulerTimeValidator;
import com.bloggios.blog.validator.implementation.businessvalidator.TitleValidator;
import com.bloggios.blog.validator.implementation.businessvalidator.TopicsValidator;
import com.bloggios.blog.validator.implementation.exhibitor.BlogRequestExhibitor;
import com.bloggios.blog.ymlparser.TopicDataParser;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BlogServiceImplementation.class})
@ExtendWith(SpringExtension.class)
class BlogServiceImplementationDiffblueTest {
    @MockBean
    private AddBlogSchedulerProcessor addBlogSchedulerProcessor;

    @MockBean
    private BlogDocumentDao blogDocumentDao;

    @MockBean
    private BlogDocumentToBlogResponseForListTransformer blogDocumentToBlogResponseForListTransformer;

    @MockBean
    private BlogDocumentToBlogResponseTransformer blogDocumentToBlogResponseTransformer;

    @MockBean
    private BlogEntityDao blogEntityDao;

    @MockBean
    private BlogEntityToDocumentPersistence blogEntityToDocumentPersistence;

    @MockBean
    private BlogListToListRequestTransformer blogListToListRequestTransformer;

    @MockBean
    private BlogRequestExhibitor blogRequestExhibitor;

    @MockBean
    private BlogRequestToBlogEntityTransformer blogRequestToBlogEntityTransformer;

    @Autowired
    private BlogServiceImplementation blogServiceImplementation;

    @MockBean
    private ChapterEntityDao chapterEntityDao;

    @MockBean
    private CoverImageLinkProcessor coverImageLinkProcessor;

    @MockBean
    private GenerateImageLinksWithModifiedHtml generateImageLinksWithModifiedHtml;

    @MockBean
    private HtmlDataManipulation htmlDataManipulation;

    @MockBean
    private TopicsValidator topicsValidator;

    /**
     * Method under test: {@link BlogServiceImplementation#addBlog(BlogRequest)}
     */
    @Test
    void testAddBlog() throws InterruptedException, ExecutionException {
        // Arrange
        doNothing().when(blogRequestExhibitor).validate(Mockito.<BlogRequest>any());
        when(generateImageLinksWithModifiedHtml.process(Mockito.<BlogRequest>any()))
                .thenReturn(new BlogImagesAndHtmlRecord(new ArrayList<>(), "Jan 1, 2020 9:00am GMT+0100"));
        when(coverImageLinkProcessor.process(Mockito.<BlogRequest>any())).thenReturn("Process");

        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setApiVersion("1.0.2");
        blogEntity.setBlogId("42");
        blogEntity.setCanonicalUrl("https://example.org/example");

        ChapterEntity chapter = new ChapterEntity();
        chapter.setApiVersion("1.0.2");
        chapter.setBlogs(new ArrayList<>());
        chapter.setChapterId("42");
        chapter.setChapterName("Chapter Name");
        chapter.setCoverImage("Cover Image");
        chapter.setDateCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        chapter.setDateUpdated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        chapter.setTopics(new ArrayList<>());
        chapter.setUserId("42");
        chapter.setVersion("1.0.2");
        blogEntity.setChapter(chapter);
        blogEntity.setCoverImage("Cover Image");
        blogEntity.setDateCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        blogEntity.setDateUpdated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        blogEntity.setDelta(ServiceConstants.COVER);
        blogEntity.setDetailsHtml("Execution time (Add Blog) : {}ms");
        blogEntity.setDetailsText("Details Text");
        blogEntity.setFeatureStatus(FeatureStatus.VISIBLE);
        blogEntity.setImageLinks(new ArrayList<>());
        blogEntity.setRemoteAddress("42 Main St");
        blogEntity.setSeoTitle("Dr");
        blogEntity.setTitle("Dr");
        blogEntity.setTopics(new ArrayList<>());
        blogEntity.setUserId("42");
        blogEntity.setVersion("1.0.2");
        when(
                blogRequestToBlogEntityTransformer.transform(Mockito.<BlogRequest>any(), Mockito.<BlogImagesAndHtmlRecord>any(),
                        Mockito.<String>any(), Mockito.<ChapterEntity>any(), Mockito.<String>any())).thenReturn(blogEntity);
        BlogDocument.BlogDocumentBuilder coverImageResult = BlogDocument.builder()
                .apiVersion("1.0.2")
                .blogId("42")
                .canonicalUrl("https://example.org/example")
                .chapterId("42")
                .coverImage("Cover Image");
        BlogDocument.BlogDocumentBuilder dateCreatedResult = coverImageResult
                .dateCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        BlogDocument.BlogDocumentBuilder featureStatusResult = dateCreatedResult
                .dateUpdated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .delta(ServiceConstants.COVER)
                .detailsHtml("Details Html")
                .detailsText("Details Text")
                .featureStatus(FeatureStatus.VISIBLE);
        BlogDocument.BlogDocumentBuilder titleResult = featureStatusResult.imageLinks(new ArrayList<>())
                .remoteAddress("42 Main St")
                .seoTitle("Dr")
                .title("Dr");
        BlogDocument buildResult = titleResult.topics(new ArrayList<>()).userId("42").version("1.0.2").build();
        when(blogEntityToDocumentPersistence.persist(Mockito.<BlogEntity>any(), Mockito.<DaoStatus>any()))
                .thenReturn(buildResult);
        when(htmlDataManipulation.process(Mockito.<String>any())).thenReturn("Process");
        doNothing().when(addBlogSchedulerProcessor).process(Mockito.<BlogRequest>any(), Mockito.<BlogEntity>any());

        // Act
        CompletableFuture<ModuleResponse> actualAddBlogResult = blogServiceImplementation.addBlog(new BlogRequest());

        // Assert
        boolean actualIsDoneResult = actualAddBlogResult.isDone();
        verify(blogEntityToDocumentPersistence).persist(isNull(), eq(DaoStatus.CREATE));
        verify(addBlogSchedulerProcessor).process(isA(BlogRequest.class), isA(BlogEntity.class));
        verify(coverImageLinkProcessor).process(isA(BlogRequest.class));
        verify(generateImageLinksWithModifiedHtml).process(isA(BlogRequest.class));
        verify(htmlDataManipulation).process(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(blogRequestToBlogEntityTransformer).transform(isA(BlogRequest.class), isA(BlogImagesAndHtmlRecord.class),
                eq("Process"), isNull(), eq("Process"));
        verify(blogRequestExhibitor).validate(isA(BlogRequest.class));
        ModuleResponse getResult = actualAddBlogResult.get();
        assertEquals("42", getResult.getId());
        assertEquals("42", getResult.getUserId());
        assertEquals("Blog Created Successfully", getResult.getMessage());
        assertTrue(actualIsDoneResult);
    }

    /**
     * Method under test:
     * {@link BlogServiceImplementation#blogList(BlogListRequest)}
     */
    @Test
    void testBlogList() {
        BlogDetailsTextValidator blogDetailsTextValidator = new BlogDetailsTextValidator();
        BlogImagesValidator blogImagesValidator = new BlogImagesValidator();
        CoverImageValidator coverImageValidator = new CoverImageValidator();
        HtmlDetailsValidator htmlDetailsValidator = new HtmlDetailsValidator();
        SchedulerTimeValidator schedulerTimeValidator = new SchedulerTimeValidator(null);
        TitleValidator titleValidator = new TitleValidator();
        BlogRequestExhibitor blogRequestExhibitor = new BlogRequestExhibitor(blogDetailsTextValidator, blogImagesValidator,
                coverImageValidator, htmlDetailsValidator, schedulerTimeValidator, titleValidator,
                new TopicsValidator(new TopicDataParser()));

        UploadFile uploadFile = new UploadFile();
        UploadImagesLinkProcessor uploadImagesLinkProcessor = new UploadImagesLinkProcessor(uploadFile,
                new StandardReactiveWebEnvironment());

        StandardReactiveWebEnvironment environment = new StandardReactiveWebEnvironment();
        GenerateImageLinksWithModifiedHtml generateImageLinksWithModifiedHtml = new GenerateImageLinksWithModifiedHtml(
                uploadImagesLinkProcessor, environment, new AddImageLinksHtmlProcessor());

        UploadFile uploadFile2 = new UploadFile();
        CoverImageLinkProcessor coverImageLinkProcessor = new CoverImageLinkProcessor(uploadFile2,
                new StandardReactiveWebEnvironment());

        BlogRequestToBlogEntityTransformer blogRequestToBlogEntityTransformer = new BlogRequestToBlogEntityTransformer(
                new StandardReactiveWebEnvironment());
        BlogEntityToDocumentPersistence blogEntityToDocumentPersistence = new BlogEntityToDocumentPersistence(
                new ModelMapper(), null);

        HtmlDataManipulation htmlDataManipulation = new HtmlDataManipulation();
        BlogListToListRequestTransformer blogListToListRequestTransformer = mock(BlogListToListRequestTransformer.class);
        TopicsValidator topicsValidator = new TopicsValidator(new TopicDataParser());
        ModelMapper modelMapper = new ModelMapper();
        BlogDocumentToBlogResponseForListTransformer blogDocumentToBlogResponseForListTransformer = new BlogDocumentToBlogResponseForListTransformer(
                modelMapper, new ProfileInternalResponseFeignCall(mock(UserProviderApplicationFeign.class)), null);

        ModelMapper modelMapper2 = new ModelMapper();
        BlogDocumentToBlogResponseTransformer blogDocumentToBlogResponseTransformer = new BlogDocumentToBlogResponseTransformer(
                modelMapper2, new ProfileInternalResponseFeignCall(mock(UserProviderApplicationFeign.class)), null, null);

        // Act and Assert
        assertThrows(BadRequestException.class,
                () -> (new BlogServiceImplementation(blogRequestExhibitor, generateImageLinksWithModifiedHtml,
                        coverImageLinkProcessor, null, blogRequestToBlogEntityTransformer, null, blogEntityToDocumentPersistence,
                        htmlDataManipulation, blogListToListRequestTransformer, null, topicsValidator,
                        blogDocumentToBlogResponseForListTransformer, blogDocumentToBlogResponseTransformer,
                        new AddBlogSchedulerProcessor(new StandardReactiveWebEnvironment(), null))).blogList(null));
    }
}
