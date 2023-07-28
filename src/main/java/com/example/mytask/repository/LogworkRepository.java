package com.example.mytask.repository;

import com.example.mytask.model.Logwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogworkRepository extends JpaRepository<Logwork, Integer> {

  Logwork findById(int id);

}
