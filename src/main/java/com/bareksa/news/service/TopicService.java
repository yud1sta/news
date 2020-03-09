package com.bareksa.news.service;

import com.bareksa.news.common.dto.TopicDTO;
import com.bareksa.news.common.dto.TopicResponseDTO;
import com.bareksa.news.common.dto.TopicSearchDTO;
import com.bareksa.news.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {
   TopicResponseDTO save(TopicDTO request);

   Page<TopicResponseDTO> findAll(Pageable pageable, TopicSearchDTO searchBy);

   TopicResponseDTO getOne(Integer id);

   Topic getOneTopic(Integer id);

   TopicResponseDTO update(Integer id, TopicDTO request);

   void delete(Integer id);

   TopicResponseDTO convertToResponse(Topic topic);
}
