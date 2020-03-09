package com.bareksa.news.common.dto;

import com.bareksa.news.common.annotations.EnumPattern;
import com.bareksa.news.common.enums.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class NewsDTO {
   @NotBlank
   @NotNull
   private String content;
   @NotNull
   private Integer topicId;

   @NotBlank
   @NotNull
   @EnumPattern(regexp = "deleted|draft|publish")
   private Status status;


   private List<Integer> tags;
}
