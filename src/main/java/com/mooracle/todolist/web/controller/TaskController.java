package com.mooracle.todolist.web.controller;

import com.mooracle.todolist.model.Task;
import com.mooracle.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

/** ENTRY LIST:
 *  Entry 10: Integrating User Data into Our App
 *
 * */

@Controller
public class TaskController {

    //initialize Service
    @Autowired
    private TaskService taskService;

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

    @RequestMapping(path = "/tasks", method = RequestMethod.POST)
    public String addTask(@ModelAttribute Task task){
        taskService.save(task);
        return "redirect:/";
    }
}
