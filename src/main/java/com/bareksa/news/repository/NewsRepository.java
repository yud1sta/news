package com.bareksa.news.repository;

import com.bareksa.news.entity.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NewsRepository extends SoftDeleteRepository<News, Integer> {
   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.topic.id = ?1 and e.deleted = 0")
   News findByTopicId(Integer id);
}
