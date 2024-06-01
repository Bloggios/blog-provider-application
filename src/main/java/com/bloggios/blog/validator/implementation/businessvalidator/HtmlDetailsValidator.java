package com.bloggios.blog.validator.implementation.businessvalidator;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.constants.ResponseErrorMessageConstants;
import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.payload.request.BlogRequest;
import com.bloggios.blog.validator.BusinessValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.businessvalidator
 * Created_on - June 01 - 2024
 * Created_at - 16:04
 */

@Component
public class HtmlDetailsValidator implements BusinessValidator<BlogRequest> {

    @Override
    public void validate(BlogRequest blogRequest) {
        String detailsHtml = blogRequest.getDetailsHtml();
        List<MultipartFile> images = blogRequest.getImages();
        if (Objects.isNull(detailsHtml)) throw new BadRequestException(DataErrorCodes.DETAILS_NOT_PRESENT);
        Document parse = Jsoup.parse(detailsHtml);
        Elements htmlImages = parse.select(ServiceConstants.IMG_TAG);
        if (!htmlImages.isEmpty()) {
            if (images == null) {
                throw new BadRequestException(DataErrorCodes.IMAGES_NULL,
                        String.format(ResponseErrorMessageConstants.IMAGES_NULL, htmlImages.size()));
            }
            if (htmlImages.size() != images.size()) {
                throw new BadRequestException(DataErrorCodes.IMAGES_LIST_SIZE_NOT_MATCHED_WITH_HTML,
                        String.format(ResponseErrorMessageConstants.IMAGES_LIST_SIZE_NOT_MATCHED_WITH_HTML, htmlImages.size()));
            }
        } else {
            if (!CollectionUtils.isEmpty(images)) {
                throw new BadRequestException(DataErrorCodes.IMAGES_LIST_SIZE_NOT_MATCHED_WITH_HTML);
            }
        }
    }
}
