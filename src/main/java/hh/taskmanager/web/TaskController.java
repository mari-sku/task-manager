package hh.taskmanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import hh.taskmanager.domain.AppUserRepository;
import hh.taskmanager.domain.CategoryRepository;
import hh.taskmanager.domain.ProjectRepository;
import hh.taskmanager.domain.Task;
import hh.taskmanager.domain.TaskRepository;

@Controller
public class TaskController {

    // Constructor injection of repositories
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final AppUserRepository appUserRepository;

    public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository,
            CategoryRepository categoryRepository, AppUserRepository appUserRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.categoryRepository = categoryRepository;
        this.appUserRepository = appUserRepository;
    }
    // ______________________________________________________________________________________________________________________________________________________________________________

    // ADD NEW TASK
    @GetMapping("/addtask")
    public String showAddTaskForm(Long projectId, Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("appusers", appUserRepository.findAll());
        return "addtask"; // addtask.html
    }

    // ADD NEW TASK (WHEN CLICKED FROM A SPECIFIC PROJECT)

    @GetMapping("/addtask/{projectId}")
    public String showAddTaskFormForProject(@PathVariable Long projectId, Model model) {
        Task task = new Task();
        projectRepository.findById(projectId).ifPresent(project -> task.setProject(project)); // the lambda pre-selects the project
        model.addAttribute("task", task);
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("appusers", appUserRepository.findAll());

        return "addtask"; // addtask.html
    }

    // SAVE TASK
    @PostMapping("/savetask")
    public String saveTask(@Valid @ModelAttribute Task task, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {        
            // bindingresult checks, if form fields are valid according to validation rules (i.e @size, @notnull, etc.)
            // if errors are found, fetch the form and existing objects in DB (projects, categories, appusers) again 
            model.addAttribute("projects", projectRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("appusers", appUserRepository.findAll());
            return "addtask"; // return to the same form, if there are errors
        }
        taskRepository.save(task);
        return "redirect:/projects";
    }

    // DELETE TASK
    @GetMapping("/deletetask/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/projects";
    }

    // EDIT TASK
    @GetMapping("/edittask/{taskId}")
    public String showEditTaskForm(@PathVariable Long taskId, Model model) {
        Task task = taskRepository.findById(taskId).orElseThrow(); //orElseThrow, because findById() returns Optional<Task>
        model.addAttribute("task", task);
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("appusers", appUserRepository.findAll());
        return "edittask"; // edittask.html
    }

    // SAVE EDITED TASK

@PostMapping("/edittask/{taskId}")
public String saveEditedTask(@PathVariable Long taskId, @Valid @ModelAttribute Task task, BindingResult bindingResult, Model model) {

    if (bindingResult.hasErrors()) {
        // if validation fails, re-render the same edit form
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("appusers", appUserRepository.findAll());
        return "edittask";
    }

    Task existingTask = taskRepository.findById(taskId).orElseThrow();

    existingTask.setTitle(task.getTitle());
    existingTask.setDescription(task.getDescription());
    existingTask.setDueDateTime(task.getDueDateTime());
    existingTask.setPrivate(task.isPrivate());
    existingTask.setCategory(task.getCategory());
    existingTask.setProject(task.getProject());

    taskRepository.save(existingTask);
    return "redirect:/projects";
}

    // TOGGLE TASK AS COMPLETED

    @PostMapping("/tasks/{id}/toggle")
    public String toggleTaskCompletion(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setCompleted(!task.isCompleted()); // If task.isCompleted() is true, !task.isCompleted() becomes false. If task.isCompleted() is false, !task.isCompleted() becomes true.
        taskRepository.save(task);
        return "redirect:/projects";
    }

}
