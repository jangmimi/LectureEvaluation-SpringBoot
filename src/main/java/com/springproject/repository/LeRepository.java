package com.springproject.repository;

import com.springproject.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeRepository extends JpaRepository<Evaluation, Long> {
}
