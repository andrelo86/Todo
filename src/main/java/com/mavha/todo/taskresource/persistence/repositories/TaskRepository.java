package com.mavha.todo.taskresource.persistence.repositories;

import com.mavha.todo.taskresource.persistence.entities.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findAll();

  Optional<Task> findById(Long id);

  Task save(Task task);

}