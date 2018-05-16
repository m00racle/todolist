package com.mooracle.todolist.dao;

import com.mooracle.todolist.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** ENTRY LIST:
 *  ENTRY 7: Implementing UserDetailsService
 *  Note: this only required us to code short syntax and the rest already included inside the CrudRepository extension. when
 *      we boot up the app Spring will automatically build the class implementing this UserDao interface.
 *  That UserDao implememntation will includes findByUsername method which required String argument called username passed
 *      onto it.
 * */

@Repository //7-2: add annotation for dao
public interface UserDao extends CrudRepository<User, Long> {//7-1: extends User which has identifier (id) type Long
    //7-3: include the method we had explicitly defined in UserService interface:
    User findByUsername(String username);
}
