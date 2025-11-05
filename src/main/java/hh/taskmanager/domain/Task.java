package hh.taskmanager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskId;

    @Column(nullable = false)
    private String title;

    private String description; // OPTIONAL

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // yyyy-MM-dd'T'HH:mm follows iso-standards, i.e html uses this format                                     
    private LocalDateTime dueDateTime; // OPTIONAL

    private boolean isCompleted;

    private boolean isPrivate;

    @ManyToOne
    @JsonIgnoreProperties ("tasks")  // to avoid infinite loop during JSON serialization
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(optional = true) 
    @JsonIgnoreProperties ("tasks")  // to avoid infinite loop during JSON serialization
    @JoinColumn(name = "project_id", nullable = true)
    private Project project; // OPTIONAL

    @ManyToOne
    @JsonIgnoreProperties ("tasks")  // to avoid infinite loop during JSON serialization
    @JoinColumn(name = "user_id")
    private AppUser assignedUser;


    // Constructors

    public Task() {
    }

    public Task(String title, String description, LocalDateTime dueDateTime, boolean isCompleted,
            boolean isPrivate, Category category, Project project, AppUser assignedUser) {
        this.title = title;
        this.description = description;
        this.dueDateTime = dueDateTime;
        this.isCompleted = false; // default is always false
        this.isPrivate = isPrivate;
        this.category = category;
        this.project = project;
        this.assignedUser = assignedUser;
    }


// if description, dueDateTime or project are not provided, they default to null
public Task(String title, boolean isCompleted, boolean isPrivate, Category category, AppUser assignedUser) {
    this.title = title;
    this.description = null;
    this.dueDateTime = null;
    this.isCompleted = isCompleted;
    this.isPrivate = isPrivate;
    this.category = category;
    this.project = null;
    this.assignedUser = assignedUser;
}


    // Getters and Setters

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isprivate) {
        this.isPrivate = isprivate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
        return "Task [title=" + title + ", description=" + description + ", dueDateTime=" + dueDateTime
                + ", isCompleted="
                + isCompleted + ", isprivate=" + isPrivate + ", category=" + category + ", project=" + project
                + ", assignedUser=" + assignedUser + "]";
    }

}
