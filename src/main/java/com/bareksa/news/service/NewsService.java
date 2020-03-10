package com.bareksa.news.service;

import com.bareksa.news.common.dto.NewsDTO;
import com.bareksa.news.common.dto.NewsResponseDTO;
import com.bareksa.news.common.dto.NewsSearchDTO;
import com.bareksa.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
   NewsResponseDTO save(NewsDTO request);

   Page<NewsResponseDTO> findAll(Pageable pageable, NewsSearchDTO searchBy);

   NewsResponseDTO getOne(Integer id);

   News getOneNews(Integer id);

   NewsResponseDTO update(Integer id, NewsDTO request);

   void delete(Integer id);

   NewsResponseDTO convertToResponse(News news);
}
