package com.mooracle.todolist.web.controller;

import com.mooracle.todolist.model.Task;
import com.mooracle.todolist.model.User;
import com.mooracle.todolist.service.TaskService;
import com.mooracle.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

/** ENTRY LIST:
 *  Entry 10: Integrating User Data into Our App
 *  Entry 19: Associating saving task with user
 * */

@Controller
public class TaskController {

    //initialize Service
    @Autowired
    private TaskService taskService;

    /*@Autowired
    private UserService userService; //19-2a: @Autowired UserService to fetch user (deprecated by 19-2b)*/

    /** 10-1: for the taskList method:
     *   in here we will include a security principal argument, and this principal object is one that will be...
     *      injected as a parameter value and encapsulate all authentication data.This will be username password ...
     *      authentication token object.
     *      we will using more indirect approach to set the username password data to authorized by using Thymeleaf
     *      dialect add ons but this require more config. Spring security dialect. Thus we actually do not need this
     *      Controller to be modified
     *
     * */
    @RequestMapping({"/", "/todo"})
    public String taskList(Model model) {//delete the Principal argument

        Iterable<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        return "todo";
    }

    @RequestMapping(path = "/mark", method = RequestMethod.POST)
    public String toggleComplete(@RequestParam Long id) {
        Task task = taskService.findOne(id);
        taskService.toggleComplete(id);
        return "redirect:/";
    }

    /** Entry 19: Associating task with user before saving
     *  1.  We'll add a principal parameter whose value will be populated with the username passsword authentication
     *      token object, which implemented the principal interface
     *  2.  there are two ways to get user entity:
     *      a.  fetchiing the username from the principal then using the user service to grab the user entity by its
     *          user name. But make sure to @Autowired UserService
     *          NOTE: this userService.findByUserName will hit the database which honestly will not be so significant
     *      b.  If you don't want to hit the database we can run quick test with output to see whether the principal
     *          parameter is populated with username and password token object. We will need to cast the principle to
     *          UsernamePasswordAuthentication token object.*/
    @RequestMapping(path = "/tasks", method = RequestMethod.POST)
    public String addTask(@ModelAttribute Task task, Principal principal){
        /*It is substituted with 19-2b:
        User user = userService.findByUsername(principal.getName());//19-2: getting the user name logged in
        task.setUser(user);//19-2: set the user to be associated with the task*/

        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();//19-2b: casting process
        task.setUser(user);//19-2b: setting the user in a task
        taskService.save(task);
        return "redirect:/";
    }
}
