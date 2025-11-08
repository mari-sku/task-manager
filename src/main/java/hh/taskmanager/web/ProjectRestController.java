package hh.taskmanager.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;


import hh.taskmanager.domain.Project;
import hh.taskmanager.domain.ProjectRepository;

@CrossOrigin
@RestController
public class ProjectRestController {

    private final ProjectRepository projectRepository;

    public ProjectRestController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
// ________________________________________________________________________________________________________________________________________________

    // find all projects
    @GetMapping("/api/projects")
    public List<Project> getAllProjects() {
        return (List<Project>) projectRepository.findAll();
    }

    // find project by id
    @GetMapping("/api/projects/{id}")
    public Optional<Project> getProjectById(@PathVariable("id") Long projectId) {
        return projectRepository.findById(projectId);
    }

    // delete project by id
    @DeleteMapping("/api/projects/{id}")
    public void deleteProject(@PathVariable("id") Long projectId) {
        projectRepository.deleteById(projectId);
    }

}
