package com.mooracle.todolist.service;

import com.mooracle.todolist.dao.UserDao;
import com.mooracle.todolist.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** ENTRY LIST:
 *  ENTRY 6: Implementing UserDetailService
 * */

@Service //6-1: this is annotated to be recognized as @Service
public class UserServiceImpl implements UserService {//6-2: implements and add all methods from UserService and...
    // ...UserDetailService

    //6-2: after building UserDao we can now @Autowired it
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);//6-3: just call the same method in the DAO
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //6-4: load user by the username, throw an exception if it is not found (expected so it's mandatory)..
        //.. we use the userDao.findByUsername method to find specific user
        User user = userDao.findByUsername(username);

        //6-5: validate if user is not found then throw
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;//6-6: return the user if it exist
    }
}
