package hh.taskmanager.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    // PROJECT NAME validation annotations
    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 5000, message = "Description must be less than 5000 characters")
    private String description; // OPTIONAL

    private boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("projects") // to avoid infinite loop during JSON serialization
    private AppUser assignedUser;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)  // when a project is deleted, its tasks are also deleted. orphanremoval needed
    @JsonIgnoreProperties("project") // to avoid infinite loop during JSON serialization
    private List<Task> tasks;


    // Constructors

    public Project() {
    }

    public Project(String name, String description, boolean isPrivate, AppUser assignedUser) {
        this.name = name;
        this.description = description;
        this.isPrivate = false; // default to false
        this.assignedUser = assignedUser;
        this.tasks = new ArrayList<>();
    }

    // if description is not provided, it defaults to null
    public Project(String name, boolean isPrivate, AppUser assignedUser) {
        this.name = name;
        this.description = null;
        this.isPrivate = isPrivate;
        this.assignedUser = assignedUser;
        this.tasks = new ArrayList<>();
    }

    // Getters and Setters

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public AppUser getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(AppUser assignedUser) {
        this.assignedUser = assignedUser;
    }

    // To String

   @Override
    public String toString() {
        return "Project [name=" + name + ", description=" + description + ", isPrivate=" + isPrivate + ", assignedUser="
                + assignedUser + "]";
    }

    
}
