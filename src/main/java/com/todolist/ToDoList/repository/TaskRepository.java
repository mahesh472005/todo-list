package com.todolist.ToDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todolist.ToDoList.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
