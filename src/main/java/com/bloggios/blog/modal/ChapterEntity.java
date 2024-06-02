package com.bloggios.blog.modal;

import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.modal.embeddable.TopicsEmbeddable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
 * Created_at - 15:24
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "chapter")
public class ChapterEntity {

    @Id
    @GeneratedValue(generator = ServiceConstants.RANDOM_UUID)
    @GenericGenerator(name = ServiceConstants.RANDOM_UUID, strategy = "org.hibernate.id.UUIDGenerator")
    private String chapterId;

    @Column(nullable = false)
    private String chapterName;

    @Column(nullable = false)
    private String userId;

    @ElementCollection
    private List<TopicsEmbeddable> topics = new ArrayList<>();

    @OneToMany(
            mappedBy = "chapter",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<BlogEntity> blogs = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;
    private String apiVersion;
    private String version;
    private String coverImage;
}
