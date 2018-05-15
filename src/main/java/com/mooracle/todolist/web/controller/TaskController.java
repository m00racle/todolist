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

@Controller
public class TaskController {

    //initialize Service
    @Autowired
    private TaskService taskService;

    @RequestMapping({"/", "/todo"})
    public String taskList(Model model) {
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
