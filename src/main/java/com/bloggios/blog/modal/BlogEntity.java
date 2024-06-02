package com.bloggios.blog.modal;

import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.enums.FeatureStatus;
import com.bloggios.blog.modal.embeddable.ImageLinksEntity;
import com.bloggios.blog.modal.embeddable.TopicsEmbeddable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.modal
 * Created_on - June 01 - 2024
 * Created_at - 15:14
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "blog")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class BlogEntity {

    @Id
    @GeneratedValue(generator = ServiceConstants.RANDOM_UUID)
    @GenericGenerator(name = ServiceConstants.RANDOM_UUID, strategy = "org.hibernate.id.UUIDGenerator")
    private String blogId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String detailsHtml;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Object delta;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String detailsText;

    @Column(nullable = false)
    private String userId;

    private String version;

    private String apiVersion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledOn;

    private String remoteAddress;

    @ElementCollection
    private List<ImageLinksEntity> imageLinks = new ArrayList<>();

    @ElementCollection
    private List<TopicsEmbeddable> topics = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private ChapterEntity chapter;

    @Enumerated(EnumType.STRING)
    private FeatureStatus featureStatus;

    private String coverImage;
}
