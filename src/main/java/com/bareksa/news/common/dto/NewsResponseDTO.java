package com.bareksa.news.common.dto;

import com.bareksa.news.common.enums.Status;
import lombok.Data;

import java.util.List;
@Data
public class NewsResponseDTO {
   private Integer id;
   private String topic;
   private String title;
   private String content;
   private Integer totalView;
   private String status;
   private List<TagsForRelationDTO> tags;
}
