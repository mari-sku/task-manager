package hh.taskmanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

import hh.taskmanager.domain.AppUser;
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
public String saveProject(@Valid @ModelAttribute Project project, BindingResult bindingResult, @AuthenticationPrincipal User currentUser, Model model) {
    if (bindingResult.hasErrors()) {
        model.addAttribute("appusers", appUserRepository.findAll());
        return "addproject";
    }

    if (currentUser != null) {
        AppUser user = appUserRepository.findByUsername(currentUser.getUsername());
        if (user != null) {
            project.setAssignedUser(user);
        }
    }

    projectRepository.save(project);
    return "redirect:/projects";
}

// DELETE PROJECT
@GetMapping("/deleteproject/{projectId}")
public String deleteProject(@PathVariable Long projectId) {
    projectRepository.deleteById(projectId);
    return "redirect:/projects";
}

// EDIT PROJECT
@GetMapping("/editproject/{projectId}")
public String showEditProjectForm(@PathVariable Long projectId, Model model) {
    Project project = projectRepository.findById(projectId).orElseThrow();
    model.addAttribute("project", project);
    model.addAttribute("appusers", appUserRepository.findAll());
    return "editproject"; // editproject.html
}

}



