package com.bareksa.news.common.dto;

import lombok.Data;

@Data
public class TopicSearchDTO {
   private String name;
   private String description;

   @Override
   public String toString() {
      String searchBy = "";
      if(name != null){
         searchBy+="&name="+name;
      }

      if(description != null){
         searchBy+="&description="+description;
      }

      return searchBy;
   }
}
