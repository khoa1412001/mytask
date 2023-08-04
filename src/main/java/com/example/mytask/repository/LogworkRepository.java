package com.example.mytask.repository;

import com.example.mytask.model.Logwork;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogworkRepository extends JpaRepository<Logwork, Integer> {

  Optional<Logwork> findById(Integer id);

}
