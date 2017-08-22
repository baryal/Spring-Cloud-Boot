package com.example.repository;

import com.example.model.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends BaseRepository<Todo, Long> {

    Todo findById(Long id);

    @Query("select t from Todo t where LOWER(t.title) LIKE LOWER(CONCAT('%',:title, '%'))")
    Todo findByTitle(@Param("title") String title);

    List<Todo> findBySearchTermNamed(@Param("searchTerm") String searchTerm);

    @Query(nativeQuery = true)
    List<Todo> findBySearchTermNamedNative(@Param("searchTerm") String searchTerm);
}
