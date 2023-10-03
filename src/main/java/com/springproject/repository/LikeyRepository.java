package com.springproject.repository;

import com.springproject.model.Likey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeyRepository extends JpaRepository<Likey, Long> {
}
