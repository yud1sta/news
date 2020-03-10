package com.bareksa.news.entity;

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
@Table(name="tags")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Tags extends ParentEntity implements Serializable {
   @Column(name = "name")
   private String name;

   @Column(name = "description")
   private String description;

   @ManyToMany(fetch = FetchType.LAZY,
         cascade = {
               CascadeType.PERSIST,
               CascadeType.MERGE
         },
         mappedBy = "tags")
   private Set<News> news = new HashSet<>();

}
