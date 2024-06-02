package com.bloggios.blog.transformer.utils;

import com.bloggios.blog.modal.embeddable.TopicsEmbeddable;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.transformer.utils
 * Created_on - June 02 - 2024
 * Created_at - 12:29
 */

@UtilityClass
public class TopicsTransformUtil {

    public static List<TopicsEmbeddable> getTopicsEmbeddableList(List<String> topics) {
        if (CollectionUtils.isEmpty(topics)) return null;
        return topics
                .stream()
                .map(topic -> TopicsEmbeddable.builder().topic(topic).build())
                .toList();
    }
}
