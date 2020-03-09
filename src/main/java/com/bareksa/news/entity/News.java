package com.bareksa.news.entity;

import com.bareksa.news.common.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "news")
public class News extends ParentEntity implements Serializable {
   @Column(name = "content")
   private String content;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "topic_id", nullable = false)
   @JsonIgnore
   private Topic topic;

   @ManyToMany(mappedBy = "news")
   @JsonIgnore
   private Set<Tags> genres = new HashSet<>();

   @Column(name = "status")
   private Status status;
}
