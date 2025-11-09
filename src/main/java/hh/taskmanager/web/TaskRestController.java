package hh.taskmanager.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import hh.taskmanager.domain.Task;
import hh.taskmanager.domain.TaskRepository;

@CrossOrigin // API can be used in requests from different domains. for example, front end is localhost:3000, backend is localhost:8080
@RestController
public class TaskRestController {

    // constructor injection of task repository

    private final TaskRepository taskRepository;

    public TaskRestController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
// ________________________________________________________________________________________________________________________________________________

    // get all tasks
    @GetMapping("/api/tasks")
    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    // get task by id
    @GetMapping("/api/tasks/{id}")
    public Optional<Task> getTaskById(@PathVariable("id") Long taskId) {
        return taskRepository.findById(taskId);
    }

    // extra helper endpoint: tasks without a project (unassigned tasks)
    @GetMapping("/api/tasks/unassigned")
    public List<Task> getUnassignedTasks() {
        return taskRepository.findByProjectIsNull();
    }

    // delete task by id
    @DeleteMapping("/api/tasks/{id}")
    public void deleteTask(@PathVariable("id") Long taskId) {
        taskRepository.deleteById(taskId);
    }

}
