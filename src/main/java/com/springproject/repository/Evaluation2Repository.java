package com.springproject.repository;

import com.springproject.model.Evaluation2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Evaluation2Repository extends JpaRepository<Evaluation2, Long> {
}
