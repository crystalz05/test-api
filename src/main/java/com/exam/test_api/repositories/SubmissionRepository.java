package com.exam.test_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.test_api.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

}
