package com.example.mytask.repository;

import com.example.mytask.model.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository<Task, Integer> {

  Task findById(int id);
}
