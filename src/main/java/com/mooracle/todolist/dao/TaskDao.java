package com.mooracle.todolist.dao;

import com.mooracle.todolist.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** ENTRY LIST
 *  Entry 15: Asscociating the correct Task to User
 *          we will use @Query to associate the List of task only the intended user id/ Using what is called JPQL
 *          (java Persistence Query Language) which we use here
 *  */
@Repository
public interface TaskDao extends CrudRepository<Task, Long> {
    @Query("select t from Task t where t.user.id=:#{principal.id}")//<- this is JPQL filtering Task t which id is...
    //... authenticated principal id.
    List<Task> findAll();
}
