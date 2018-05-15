package com.mooracle.todolist.dao;

import com.mooracle.todolist.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDao extends CrudRepository<Task, Long> {
}
