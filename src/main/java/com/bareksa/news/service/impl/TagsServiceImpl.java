package com.bareksa.news.service.impl;

import com.bareksa.news.common.dto.TagsDTO;
import com.bareksa.news.common.dto.TagsResponseDTO;
import com.bareksa.news.common.dto.TagsSearchDTO;
import com.bareksa.news.common.exception.InvalidParamException;
import com.bareksa.news.common.exception.ResourceExistException;
import com.bareksa.news.common.exception.ResourceNotFoundException;
import com.bareksa.news.entity.News;
import com.bareksa.news.entity.Tags;
import com.bareksa.news.repository.NewsRepository;
import com.bareksa.news.repository.TagsRepository;
import com.bareksa.news.service.TagsService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TagsServiceImpl implements TagsService {
   private TagsRepository tagsRepository;
   private NewsRepository newsRepository;
   private final String RESOURCE = "Tags";
   
   public TagsServiceImpl(TagsRepository tagsRepository, NewsRepository newsRepository) {
      this.newsRepository = newsRepository;
      this.tagsRepository = tagsRepository;
   }

   @Override
   public TagsResponseDTO save(TagsDTO request) {
      // check tags if already exist
      Tags tags = tagsRepository.findByName(request.getName());

      if (tags != null) {
         throw new ResourceExistException(request.getName(), "Name", RESOURCE);
      } else {
         tags = new Tags();
      }

      BeanUtils.copyProperties(request, tags);

      tags.setCreateTime(new Date());
      tags.setDeleted(false);
      tags.setDeleteTime(null);

      Tags tagsSave = tagsRepository.save(tags);
      return convertToResponse(tagsSave);
   }

   @Override
   public Page<TagsResponseDTO> findAll(Pageable pageable, TagsSearchDTO searchBy) {
      Page<TagsResponseDTO> tags = null;
      if(searchBy.getName() != null && searchBy.getDescription() != null){
         tags =tagsRepository.findAllByNameAndDescription(pageable, searchBy.getName(), searchBy.getDescription()).map(this::convertToResponse);
      }
      else if(searchBy.getName() != null && searchBy.getDescription() == null){
         tags = tagsRepository.findAllByName(pageable, searchBy.getName()).map(this::convertToResponse);
      }
      else if(searchBy.getDescription() != null && searchBy.getName() == null){
         tags = tagsRepository.findAllByDescription(pageable, searchBy.getDescription()).map(this::convertToResponse);
      }else{
         tags = tagsRepository.findAll(pageable).map(this::convertToResponse);
      }

      return tags;
   }

   @Override
   public TagsResponseDTO getOne(Integer id) {
      return tagsRepository.findById(id).map(this::convertToResponse)
            .orElseThrow(() -> new ResourceNotFoundException(id,"Id", RESOURCE));
   }

   @Override
   public Tags getOneTags(Integer id) {
      return tagsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id,"Id", RESOURCE));
   }

   @Override
   public TagsResponseDTO update(Integer id, TagsDTO request) {
      Tags tags = getOneTags(id);

      BeanUtils.copyProperties(request, tags);
      tags.setUpdateTime(new Date());

      return convertToResponse(tagsRepository.save(tags));
   }

   @Override
   public void delete(Integer id) {
      Tags tags = getOneTags(id);

      News news = newsRepository.findByTagId(id);

      if(news != null){
         throw new InvalidParamException("cannot be deleted because it has a relationship with news");
      }

      tagsRepository.delete(tags);
   }

   @Override
   public TagsResponseDTO convertToResponse(Tags tags) {
      TagsResponseDTO tagsResponse = new TagsResponseDTO();
      BeanUtils.copyProperties(tags, tagsResponse);
      return tagsResponse;
   }
}
