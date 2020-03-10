package com.bareksa.news.entity;

import com.bareksa.news.common.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name="news")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class News extends ParentEntity implements Serializable {
   @Column(name = "title")
   private String title;

   @Column(name = "content")
   private String content;

   @Column(name = "total_view")
   private Integer totalView;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "topic_id", nullable = false)
   @JsonIgnore
   private Topic topic;

   @ManyToMany(fetch = FetchType.LAZY,
         cascade = {
               CascadeType.PERSIST,
               CascadeType.MERGE
         })
   @JoinTable(name = "news_tags",
         joinColumns = { @JoinColumn(name = "news_id") },
         inverseJoinColumns = { @JoinColumn(name = "tags_id") })
   private Set<Tags> tags = new HashSet<>();

   @Column(name = "status")
   private String status;
}
