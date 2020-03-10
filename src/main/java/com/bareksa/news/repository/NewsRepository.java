package com.bareksa.news.repository;

import com.bareksa.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NewsRepository extends SoftDeleteRepository<News, Integer> {
   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.title = ?1 and e.deleted = 0")
   News findByTitle(String title);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.topic.id = ?1 and e.deleted = 0")
   News findByTopicId(Integer id);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.topic.id = ?1 and e.deleted = 0")
   News findByTagId(Integer id);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.topic.name like %?1% and e.status =?2 and e.deleted = 0")
   Page<News> findAllByStatusAndTopic(Pageable pageable, String topic, String status);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.topic.name like %?1% and e.deleted = 0")
   Page<News> findAllByTopic(Pageable pageable,String topic);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.status = ?1  and e.deleted = 0")
   Page<News> findAllByStatus(Pageable pageable, String status);

}
