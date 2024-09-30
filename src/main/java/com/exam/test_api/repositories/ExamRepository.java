package com.exam.test_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.test_api.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {

}
