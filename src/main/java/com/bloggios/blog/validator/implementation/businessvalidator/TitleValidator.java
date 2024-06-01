package com.bloggios.blog.validator.implementation.businessvalidator;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.utils.WordsCounter;
import com.bloggios.blog.validator.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.businessvalidator
 * Created_on - June 01 - 2024
 * Created_at - 15:55
 */

@Component
public class TitleValidator implements BusinessValidator<String> {

    @Override
    public void validate(String title) {
        if (Objects.isNull(title) || ObjectUtils.isEmpty(title)) {
            throw new BadRequestException(DataErrorCodes.TITLE_MANDATORY);
        }
        int words = WordsCounter.countWords(title);
        if (words > 25) {
            throw new BadRequestException(DataErrorCodes.TITLE_WORD_LIMIT_EXCEED);
        }
        if (title.length() > 150) {
            throw new BadRequestException(DataErrorCodes.TITLE_LENGTH_LIMIT_EXCEED);
        }
    }
}
