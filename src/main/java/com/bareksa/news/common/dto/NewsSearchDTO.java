package com.bareksa.news.common.dto;

import lombok.Data;

@Data
public class NewsSearchDTO {
   private String topic;
   private String status;

   @Override
   public String toString() {
      String searchBy = "";
      if(topic != null){
         searchBy+="&topic="+topic;
      }

      if(status != null){
         searchBy+="&status="+status;
      }

      return searchBy;
   }
}
