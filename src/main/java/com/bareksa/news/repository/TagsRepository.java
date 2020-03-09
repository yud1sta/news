package com.bareksa.news.repository;

import com.bareksa.news.entity.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TagsRepository extends SoftDeleteRepository<Tags, Integer> {
   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.name = ?1 and e.deleted = 0")
   Tags findByName(String name);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.name like %?1% and e.description like %?2% and e.deleted = 0")
   Page<Tags> findAllByNameAndDescription(Pageable pageable, String name, String description);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.name like %?1%  and e.deleted = 0")
   Page<Tags> findAllByName(Pageable pageable, String name);

   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.description like %?1% and e.deleted = 0")
   Page<Tags> findAllByDescription(Pageable pageable, String description);
}
