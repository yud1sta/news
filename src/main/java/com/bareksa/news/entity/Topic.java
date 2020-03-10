package com.bareksa.news.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@DynamicUpdate
@Table(name="topic")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Topic extends ParentEntity implements Serializable {
   @Column(name = "name")
   private String name;

   @Column(name = "description")
   private String description;
}
