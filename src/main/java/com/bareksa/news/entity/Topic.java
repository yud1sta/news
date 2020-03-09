package com.bareksa.news.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "topic")
public class Topic extends ParentEntity implements Serializable {
   @Column(name = "name")
   private String name;

   @Column(name = "description")
   private String description;
}
