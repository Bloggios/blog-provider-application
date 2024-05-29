package com.bloggios.blog.ymlparser;

import com.bloggios.blog.payload.TagYmlDataProvider;
import com.bloggios.blog.ymlparser.factory.YmlFileMapParserFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.ymlparser
 * Created_on - May 29 - 2024
 * Created_at - 20:52
 */

@PropertySource(value = "classpath:tags-data.yml", factory = YmlFileMapParserFactory.class)
@Configuration
@ConfigurationProperties(prefix = "tag-data")
@Getter
@Setter
public class TagDataParser {

    public final Map<String, TagYmlDataProvider> provider = new HashMap<>();
}
