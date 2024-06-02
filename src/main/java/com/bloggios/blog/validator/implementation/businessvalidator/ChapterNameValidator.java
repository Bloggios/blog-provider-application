package com.bloggios.blog.validator.implementation.businessvalidator;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.dao.implementation.pgsqlimplementation.ChapterEntityDao;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.modal.ChapterEntity;
import com.bloggios.blog.payload.request.ChapterRequest;
import com.bloggios.blog.utils.WordsCounter;
import com.bloggios.blog.validator.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.businessvalidator
 * Created_on - June 02 - 2024
 * Created_at - 12:17
 */

@Component
public class ChapterNameValidator implements BusinessValidator<ChapterRequest> {

    private final ChapterEntityDao chapterEntityDao;

    public ChapterNameValidator(ChapterEntityDao chapterEntityDao) {
        this.chapterEntityDao = chapterEntityDao;
    }

    @Override
    public void validate(ChapterRequest chapterRequest) {
        String chapterName = chapterRequest.getChapterName();
        if (ObjectUtils.isEmpty(chapterName)) {
            throw new BadRequestException(DataErrorCodes.CHAPTER_NAME_MANDATORY);
        }
        if (chapterName.length() > 50) {
            throw new BadRequestException(DataErrorCodes.CHAPTER_NAME_LENGTH_EXCEED);
        }
        int words = WordsCounter.countWords(chapterName);
        if (words > 7) {
            throw new BadRequestException(DataErrorCodes.CHAPTER_WORD_LIMIT_EXCEED);
        }
        Optional<ChapterEntity> byUserIdAndChapterName = chapterEntityDao.findByUserIdAndChapterName(chapterRequest.getAuthenticatedUser().getUserId(), chapterName.toLowerCase());
        if (byUserIdAndChapterName.isPresent()) {
            throw new BadRequestException(DataErrorCodes.CHAPTER_NAME_ALREADY_EXISTS);
        }
    }
}
