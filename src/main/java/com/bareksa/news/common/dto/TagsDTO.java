package com.bareksa.news.common.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TagsDTO {
   @NotBlank
   @NotNull
   private String name;

   private String description;
}
