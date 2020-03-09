package com.bareksa.news.service.impl;

import com.bareksa.news.common.dto.TopicDTO;
import com.bareksa.news.common.dto.TopicResponseDTO;
import com.bareksa.news.common.dto.TopicSearchDTO;
import com.bareksa.news.common.exception.InvalidParamException;
import com.bareksa.news.common.exception.ResourceExistException;
import com.bareksa.news.common.exception.ResourceNotFoundException;
import com.bareksa.news.entity.News;
import com.bareksa.news.entity.Topic;
import com.bareksa.news.repository.NewsRepository;
import com.bareksa.news.repository.TopicRepository;
import com.bareksa.news.service.TopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TopicServiceImpl implements TopicService {
   private TopicRepository topicRepository;
   private NewsRepository newsRepository;

   @Autowired
   public TopicServiceImpl(TopicRepository topicRepository, NewsRepository newsRepository){
      this.topicRepository = topicRepository;
      this.newsRepository =  newsRepository;
   }

   private static final String RESOURCE = "Topic";

   @Override
   public TopicResponseDTO save(TopicDTO request) {
      // check topic if already exist
      Topic topic = topicRepository.findByName(request.getName());

      if (topic != null) {
            throw new ResourceExistException(request.getName(), "Name", RESOURCE);
      } else {
         topic = new Topic();
      }

      BeanUtils.copyProperties(request, topic);

      topic.setCreateTime(new Date());
      topic.setDeleted(false);
      topic.setDeleteTime(null);

      Topic topicSave = topicRepository.save(topic);
      return convertToResponse(topicSave);
   }

   @Override
   public Page<TopicResponseDTO> findAll(Pageable pageable, TopicSearchDTO searchBy) {
      Page<TopicResponseDTO> topic = null;
      if(searchBy.getName() != null && searchBy.getDescription() != null){
          topic = topicRepository.findAllByNameAndDescription(pageable, searchBy.getName(), searchBy.getDescription()).map(this::convertToResponse);
      }
      else if(searchBy.getName() != null && searchBy.getDescription() == null){
         topic = topicRepository.findAllByName(pageable, searchBy.getName()).map(this::convertToResponse);
      }
      else if(searchBy.getDescription() != null && searchBy.getName() == null){
         topic = topicRepository.findAllByDescription(pageable, searchBy.getDescription()).map(this::convertToResponse);
      }else{
         topic = topicRepository.findAll(pageable).map(this::convertToResponse);
      }

      return topic;
   }

   @Override
   public TopicResponseDTO getOne(Integer id) {
      return topicRepository.findById(id).map(this::convertToResponse)
            .orElseThrow(() -> new ResourceNotFoundException(id,"Id", RESOURCE));
   }

   @Override
   public Topic getOneTopic(Integer id) {
      return topicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id,"Id", RESOURCE));
   }

   @Override
   public TopicResponseDTO update(Integer id, TopicDTO request) {
      Topic topic = getOneTopic(id);

      BeanUtils.copyProperties(request, topic);
      topic.setUpdateTime(new Date());

      return convertToResponse(topicRepository.save(topic));
   }

   @Override
   public void delete(Integer id) {
      Topic topic = getOneTopic(id);

      News news = newsRepository.findByTopicId(id);

      if(news != null){
         throw new InvalidParamException("cannot be deleted because it has a relationship with news");
      }

      topicRepository.delete(topic);
   }

   public TopicResponseDTO convertToResponse(Topic topic){
      TopicResponseDTO topicResponse = new TopicResponseDTO();
      BeanUtils.copyProperties(topic, topicResponse);
      return topicResponse;
   }


}
