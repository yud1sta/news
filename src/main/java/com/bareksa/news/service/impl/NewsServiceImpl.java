package com.bareksa.news.service.impl;

import com.bareksa.news.common.dto.NewsDTO;
import com.bareksa.news.common.dto.NewsResponseDTO;
import com.bareksa.news.common.dto.NewsSearchDTO;
import com.bareksa.news.common.dto.TagsForRelationDTO;
import com.bareksa.news.common.enums.Status;
import com.bareksa.news.common.exception.InvalidParamException;
import com.bareksa.news.common.exception.ResourceExistException;
import com.bareksa.news.common.exception.ResourceNotFoundException;
import com.bareksa.news.entity.News;
import com.bareksa.news.entity.Tags;
import com.bareksa.news.entity.Topic;
import com.bareksa.news.repository.NewsRepository;
import com.bareksa.news.repository.TagsRepository;
import com.bareksa.news.repository.TopicRepository;
import com.bareksa.news.service.NewsService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
   private NewsRepository newsRepository;
   private TopicRepository topicRepository;
   private TagsRepository tagsRepository;

   @Autowired
   public NewsServiceImpl(NewsRepository newsRepository, TopicRepository topicRepository,TagsRepository tagsRepository ){
      this.newsRepository = newsRepository;
      this.topicRepository = topicRepository;
      this.tagsRepository = tagsRepository;
   }

   private static final String RESOURCE = "News";

   @Override
   public NewsResponseDTO save(NewsDTO request) {
      // check status news
      if(!EnumUtils.isValidEnum(Status.class, request.getStatus())) {
         throw new InvalidParamException("only status draft, deleted, publish are allowed");
      }

      // check news if already exist
      News news = newsRepository.findByTitle(request.getTitle());

      if (news != null) {
         throw new ResourceExistException(request.getTitle(), "Title", RESOURCE);
      } else {
         news = new News();
      }

      BeanUtils.copyProperties(request, news);

      // check topic
      Topic topic = topicRepository.findById(request.getTopicId())
                     .orElseThrow(() -> new ResourceNotFoundException(request.getTopicId(),"Id", "Topic"));

      news.setTopic(topic);
      news.setTotalView(0);
      news.setCreateTime(new Date());
      news.setDeleted(false);
      news.setDeleteTime(null);


      if(!request.getTags().isEmpty()){
         for (Integer tagId : request.getTags()){
            // if tags doesnt exist throw exception
            Tags tag =  tagsRepository.findById(tagId)
                  .orElseThrow(() -> new ResourceNotFoundException(tagId,"Id", "Tag"));
            news.getTags().add(tag);
            tag.getNews().add(news);
         }
      }

      News newsSave = newsRepository.save(news);
      return convertToResponse(newsSave);
   }

   @Override
   public Page<NewsResponseDTO> findAll(Pageable pageable, NewsSearchDTO searchBy) {
      Page<NewsResponseDTO> news = null;
      if(searchBy.getTopic() != null && searchBy.getStatus() != null){
         // check status news
         if(!EnumUtils.isValidEnum(Status.class, searchBy.getStatus())) {
            throw new InvalidParamException("only status draft, deleted, publish are allowed");
         }
         news = newsRepository.findAllByStatusAndTopic(pageable, searchBy.getTopic(),searchBy.getStatus()).map(this::convertToResponse);
      }
      else if(searchBy.getTopic() != null && searchBy.getStatus() == null){
         news = newsRepository.findAllByTopic(pageable, searchBy.getTopic()).map(this::convertToResponse);
      }
      else if(searchBy.getStatus() != null && searchBy.getTopic() == null){
         // check status news
         if(!EnumUtils.isValidEnum(Status.class, searchBy.getStatus())) {
            throw new InvalidParamException("only status draft, deleted, publish are allowed");
         }
         news = newsRepository.findAllByStatus(pageable, searchBy.getStatus()).map(this::convertToResponse);
      }else{
         news = newsRepository.findAll(pageable).map(this::convertToResponse);
      }

      return news;
   }

   @Override
   public NewsResponseDTO getOne(Integer id) {
      News news = newsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id,"Id", RESOURCE));

      news.setTotalView(news.getTotalView()+1);
      return  convertToResponse(newsRepository.save(news));
   }

   @Override
   public News getOneNews(Integer id) {
      return newsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id,"Id", RESOURCE));
   }

   @Override
   public NewsResponseDTO update(Integer id, NewsDTO request) {
      // check status news
      if(!EnumUtils.isValidEnum(Status.class, request.getStatus())) {
         throw new InvalidParamException("only status draft, deleted, publish are allowed");
      }

      News news = getOneNews(id);

      BeanUtils.copyProperties(request, news);
      news.setUpdateTime(new Date());

      // check topic
      Topic topic = topicRepository.findById(request.getTopicId())
            .orElseThrow(() -> new ResourceNotFoundException(request.getTopicId(),"Id", "Topic"));

      news.setTopic(topic);

       // remove all relation before update
      for (Tags tag: news.getTags()){
         tag.getNews().remove(news);
         news.getTags().remove(tag);
      }

      if(!request.getTags().isEmpty()){
         for (Integer tagId : request.getTags()){
            // if tags doesnt exist throw exception
            Tags tag =  tagsRepository.findById(tagId)
                  .orElseThrow(() -> new ResourceNotFoundException(tagId,"Id", "Tag"));
            news.getTags().add(tag);
            tag.getNews().add(news);
         }
      }

      return convertToResponse(newsRepository.save(news));
   }

   @Override
   public void delete(Integer id) {
      News news = getOneNews(id);

      newsRepository.delete(news);
   }

   public NewsResponseDTO convertToResponse(News news){
      NewsResponseDTO newsResponse = new NewsResponseDTO();
      BeanUtils.copyProperties(news, newsResponse);
      newsResponse.setTopic(news.getTopic().getName());

      // create tags
      List<TagsForRelationDTO> tags = new ArrayList<>();
      news.getTags().forEach(e->{
         TagsForRelationDTO item = new TagsForRelationDTO();
         item.setId(e.getId());
         item.setName(e.getName());
         tags.add(item);
      });

      newsResponse.setTags(tags);

      return newsResponse;
   }


}
