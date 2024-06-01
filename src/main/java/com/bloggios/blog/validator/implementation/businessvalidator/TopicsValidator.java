package com.bloggios.blog.validator.implementation.businessvalidator;

import com.bloggios.blog.constants.DataErrorCodes;
import com.bloggios.blog.constants.ResponseErrorMessageConstants;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.payload.TopicsYmlDataProvider;
import com.bloggios.blog.validator.BusinessValidator;
import com.bloggios.blog.ymlparser.TopicDataParser;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.validator.implementation.businessvalidator
 * Created_on - June 01 - 2024
 * Created_at - 16:35
 */

@Component
public class TopicsValidator implements BusinessValidator<List<String>> {

    private final TopicDataParser topicDataParser;

    public TopicsValidator(
            TopicDataParser topicDataParser
    ) {
        this.topicDataParser = topicDataParser;
    }

    @Override
    public void validate(List<String> strings) {
        if (!CollectionUtils.isEmpty(strings)) {
            List<String> storedTopics = topicDataParser
                    .provider
                    .values()
                    .stream()
                    .map(TopicsYmlDataProvider::getTag)
                    .toList();

            if (strings.size() > 5) {
                throw new BadRequestException(DataErrorCodes.TAGS_LIMIT_EXCEED);
            }
            strings.forEach(topic -> {
                if (Pattern.matches("\\s+", topic)) {
                    throw new BadRequestException(DataErrorCodes.BLANK_SPACING_IN_TAG);
                }
            });
            Map<Boolean, List<String>> partitionedList = strings
                    .parallelStream()
                    .collect(Collectors.partitioningBy(storedTopics::contains));
            List<String> notPresent = partitionedList.get(false);
            if (!CollectionUtils.isEmpty(notPresent)) {
                throw new BadRequestException(
                        DataErrorCodes.TAGS_NOT_PRESENT,
                        String.format(ResponseErrorMessageConstants.TAGS_NOT_PRESENT, String.join(", ", notPresent))
                );
            }
        }
    }
}
