package com.bareksa.news.common.dto;

import com.bareksa.news.common.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NewsDTO {
   @NotBlank
   @NotNull
   private String title;

   @NotBlank
   @NotNull
   private String content;
   @NotNull
   private Integer topicId;

   @NotNull
   private String status;


   private List<Integer> tags;
}
