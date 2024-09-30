package com.exam.test_api.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exam.test_api.model.Question;

public interface QuestionsRepository extends JpaRepository<Question, Long> {
//	Optional<Question> findByType(String title);
	
    @Query("SELECT q FROM Question q WHERE q.type = :type")
    List<Question> findByType(@Param("type") String type, Pageable pageable);

}
