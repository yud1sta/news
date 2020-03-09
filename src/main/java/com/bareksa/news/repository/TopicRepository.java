package com.bareksa.news.repository;

import com.bareksa.news.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TopicRepository extends SoftDeleteRepository<Topic, Integer> {
   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.name = ?1 and e.deleted = 0")
   Topic findByName(String name);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.name like %?1% and e.description like %?2% and e.deleted = 0")
   Page<Topic> findAllByNameAndDescription(Pageable pageable, String name, String description);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.name like %?1%  and e.deleted = 0")
   Page<Topic> findAllByName(Pageable pageable, String name);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.description like %?1% and e.deleted = 0")
   Page<Topic> findAllByDescription(Pageable pageable, String description);
}
