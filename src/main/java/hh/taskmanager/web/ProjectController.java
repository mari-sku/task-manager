package hh.taskmanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hh.taskmanager.domain.AppUserRepository;
import hh.taskmanager.domain.Project;
import hh.taskmanager.domain.ProjectRepository;

@Controller
public class ProjectController {
    
    // Constructor injection of repositories
    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;

    public ProjectController(ProjectRepository projectRepository, AppUserRepository appUserRepository) {
        this.projectRepository = projectRepository;
        this.appUserRepository = appUserRepository;
    }
//______________________________________________________________________________________________________________________________________________________________________________

//ADD NEW PROJECT FORM
@GetMapping("/addproject")
public String showAddProjectForm(Model model) {
    model.addAttribute("project", new Project());
    model.addAttribute("appusers", appUserRepository.findAll());  // to select owner of the project
    return "addproject";  // addproject.html
}

// SAVE PROJECT
@PostMapping("/saveproject")
public String saveProject(@ModelAttribute Project project) {
    projectRepository.save(project);
    return "redirect:/projects"; 
}











}



