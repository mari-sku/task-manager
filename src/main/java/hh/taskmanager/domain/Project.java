package hh.taskmanager.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
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
    private long projectId;

    @Column(nullable = false)
    private String name;

    private String description; // OPTIONAL

    private boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("projects") // to avoid infinite loop during JSON serialization
    private AppUser assignedUser;

    @OneToMany(mappedBy = "project")
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
    }

    // if description is not provided, it defaults to null
    public Project(String name, boolean isPrivate, AppUser assignedUser) {
        this.name = name;
        this.description = null;
        this.isPrivate = isPrivate;
        this.assignedUser = assignedUser;
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
