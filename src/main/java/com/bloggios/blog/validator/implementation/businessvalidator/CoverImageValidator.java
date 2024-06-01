package com.bloggios.blog.validator.implementation.businessvalidator;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.validator.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.businessvalidator
 * Created_on - June 01 - 2024
 * Created_at - 19:09
 */

@Component
public class CoverImageValidator implements BusinessValidator<BlogRequest> {

    @Override
    public void validate(BlogRequest blogRequest) {
        if (Objects.nonNull(blogRequest.getCoverImage())) {
            this.validateImage(blogRequest.getCoverImage());
        }
    }

    private void validateImage(MultipartFile multipartFile) {
        if (!isImageByExtension(multipartFile)) throw new BadRequestException(DataErrorCodes.COVER_NOT_IMAGE_TYPE);
        DataSize dataSize = DataSize.ofMegabytes(1);
        if (multipartFile.getSize() > dataSize.toBytes()) {
            throw new BadRequestException(DataErrorCodes.COVER_IMAGE_SIZE_LIMIT_EXCEED);
        }
    }

    private static boolean isImageByExtension(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (Objects.isNull(originalFilename)) {
            throw new BadRequestException(DataErrorCodes.COVER_INVALID_IMAGE_NAME);
        }
        String fileExtension = getFileExtension(originalFilename);
        return isImageExtension(fileExtension);
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private static boolean isImageExtension(String extension) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "bmp", "svg"};
        for (String a : imageExtensions) {
            if (extension.equals(a)) {
                return true;
            }
        }
        return false;
    }
}
