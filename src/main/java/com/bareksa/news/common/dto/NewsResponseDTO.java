package com.bareksa.news.common.dto;

import com.bareksa.news.common.enums.Status;
import lombok.Data;

import java.util.List;
@Data
public class NewsResponseDTO {
   private Integer id;
   private String content;
   private String topic;
   private Status status;
   private List<TagsForRelationDTO> tags;
}
