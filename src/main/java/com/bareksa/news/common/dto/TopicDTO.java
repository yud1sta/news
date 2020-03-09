package com.bareksa.news.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TopicDTO {
   @NotBlank
   @NotNull
   private String name;

   private String description;
}
