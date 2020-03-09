package com.bareksa.news.service;

import com.bareksa.news.common.dto.*;
import com.bareksa.news.entity.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagsService {
   TagsResponseDTO save(TagsDTO request);

   Page<TagsResponseDTO> findAll(Pageable pageable, TagsSearchDTO searchBy);

   TagsResponseDTO getOne(Integer id);

   Tags getOneTags(Integer id);

   TagsResponseDTO update(Integer id, TagsDTO request);

   void delete(Integer id);

   TagsResponseDTO convertToResponse(Tags tags);
}
