package com.bareksa.news.repository;

import com.bareksa.news.entity.ParentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SoftDeleteRepository<T extends ParentEntity, ID extends Integer> extends JpaRepository<T, ID> {

   @Override
   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.deleted = 0")
   List<T> findAll();

   @Override
   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.deleted = 0")
   Page<T> findAll(Pageable pageable);

   @Override
   @Transactional(readOnly = true)
   @Query("select e from #{#entityName} e where e.id = ?1 and e.deleted = 0")
   Optional<T> findById(ID id);

   @Override
   @Query("update #{#entityName} e set e.deleted=1, e.deleteTime = current_timestamp where e.id = ?1 ")
   @Transactional
   @Modifying
   void deleteById(Integer id);

   @Override
   @Transactional
   default void delete(T entity) {
      deleteById(entity.getId());
   }

   //Look up deleted entities
   @Query("select e from #{#entityName} e where e.deleted = 1")
   @Transactional(readOnly = true)
   List<T> findDeleted();

   @Override
   @Transactional(readOnly = true)
   @Query("select count(e) from #{#entityName} e where e.deleted = 0")
   long count();

}
