package com.example.demo.repositories;

import com.example.demo.models.Credit;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Long> {
    List<Credit> findByUser(User user);
}
