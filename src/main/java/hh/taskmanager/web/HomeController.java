package hh.taskmanager.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hh.taskmanager.domain.ProjectRepository;
import hh.taskmanager.domain.TaskRepository;


// http://localhost:8080/projects

@Controller
public class HomeController {

// Constructor injection of repositories
private ProjectRepository projectRepository;
private TaskRepository taskRepository;

public HomeController(ProjectRepository projectRepository, TaskRepository taskRepository) {
    this.projectRepository = projectRepository;
    this.taskRepository = taskRepository;
}
//_____________________________________________________________________________________________________________________________________________________________________________

// "HOME" page showing all projects and unassigned tasks (tasks that don’t belong to a project)
@GetMapping({"/", "/projects"})
public String showHomePage(Model model, Authentication authentication) {
      model.addAttribute("projects", projectRepository.findAll());
      model.addAttribute("unassignedTasks", taskRepository.findByProjectIsNull()); // findByProjectIsNull() is defined in TaskRepository. Spring generates the query automatically :)

      // checking if user is authenticated (logged in)
      if (authentication != null && authentication.isAuthenticated()) {
        model.addAttribute("loggedIn", true);
        model.addAttribute("username", authentication.getName());
    } else {
        model.addAttribute("loggedIn", false);
    }
    
    return "projects";  // projects.html
    
    
}

}
