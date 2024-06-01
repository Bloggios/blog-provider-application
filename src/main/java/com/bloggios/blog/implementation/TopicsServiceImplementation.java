package com.bloggios.blog.implementation;

import com.bloggios.blog.constants.BeanConstants;
import com.bloggios.blog.payload.TopicsYmlDataProvider;
import com.bloggios.blog.payload.response.TopicsListResponse;
import com.bloggios.blog.service.TopicsService;
import com.bloggios.blog.ymlparser.TopicDataParser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.implementation
 * Created_on - May 30 - 2024
 * Created_at - 21:11
 */

@Service
public class TopicsServiceImplementation implements TopicsService {

    private final TopicDataParser topicDataParser;

    public TopicsServiceImplementation(
            TopicDataParser topicDataParser
    ) {
        this.topicDataParser = topicDataParser;
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<TopicsListResponse> tagsList() {
        Map<String, TopicsYmlDataProvider> provider = topicDataParser.provider;
        List<TopicsYmlDataProvider> topicsYmlDataProviderList = provider.values().stream().toList();
        TopicsListResponse build = TopicsListResponse
                .builder()
                .object(topicsYmlDataProviderList)
                .build();
        return CompletableFuture.completedFuture(build);
    }
}
