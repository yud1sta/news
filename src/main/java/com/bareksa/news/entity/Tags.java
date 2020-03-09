package com.bareksa.news.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "tags")
public class Tags extends ParentEntity implements Serializable {
   @Column(name = "name")
   private String name;

   @Column(name = "description")
   private String description;

   @ManyToMany
   @JoinTable(name = "news_tags",
         joinColumns = @JoinColumn(name = "tags_id"),
         inverseJoinColumns = @JoinColumn(name = "news_id")
   )
   @JsonIgnore
   Set<News> news  =  new HashSet<>();
}
