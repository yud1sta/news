package com.bareksa.news.entity;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public class ParentEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   protected Integer id;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "create_time")
   protected Date createTime;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "update_time")
   protected Date updateTime;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "delete_time")
   protected Date deleteTime;

   @Column(name = "is_deleted")
   protected Boolean deleted;
}
