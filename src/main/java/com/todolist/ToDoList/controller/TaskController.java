package com.todolist.ToDoList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todolist.ToDoList.model.Task;
import com.todolist.ToDoList.model.User;
import com.todolist.ToDoList.repository.TaskRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public String showTaskList(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "task-list";  // Renders task-list.html
    }

    @PostMapping("/add")
    public String addTask(@RequestParam("description") String description) {
        Task task = new Task();
        task.setDescription(description);
        task.setCompleted(false);
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setCompleted(!task.isCompleted()); // Toggle completion status
            taskRepository.save(task);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }
    @GetMapping("/tasks")
    public String showTaskList(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");

    if (user == null) {
        return "redirect:/login"; // Redirect if not logged in
    }

    List<Task> tasks = taskRepository.findAll();
    model.addAttribute("tasks", tasks);
    return "task-list";
}


}
