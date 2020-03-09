package com.bareksa.news.common.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TopicResponseDTO {
   private Integer id;
   private String name;
   private String description;
   private Date createTime;
   private Date updateTime;

}
