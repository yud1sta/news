package com.bareksa.news.common.dto;

import com.bareksa.news.common.enums.Status;

import java.util.List;

public class NewsResponseDTO {
   private String content;
   private String topic;
   private Status status;
   private List<TagsForRelationDTO> tags;
}
