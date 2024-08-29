package com.bloggios.blog.modal;

import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.enums.ScheduledTaskType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.modal
 * Created_on - August 29 - 2024
 * Created_at - 18:29
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = ServiceConstants.SCHEDULER_TABLE)
public class SchedulerData {

    @Id
    @GeneratedValue(generator = ServiceConstants.RANDOM_UUID)
    @GenericGenerator(name = ServiceConstants.RANDOM_UUID, strategy = "org.hibernate.id.UUIDGenerator")
    private String schedulerId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    private String destId;
    private String apiVersion;

    @Column(columnDefinition = "boolean default false")
    private boolean isSchedulingDone;

    @Enumerated(EnumType.STRING)
    private ScheduledTaskType scheduledTaskType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleCompletedOn;
}
