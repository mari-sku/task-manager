package hh.taskmanager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    // TASK TITLE  validation annotations
    @NotBlank(message = "Task title is required")
    @Size(min = 2, max = 150, message = "Title must be between 2 and 150 characters")
    private String title;

    //TASK DESCRIPTION validation annotations
// task description is not shown anywhere on the html templates yet, should be implemented later!!!
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description; // OPTIONAL

    // DUEDATETIME validation annotation
    @FutureOrPresent(message = "Due date must be in the present or future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // yyyy-MM-dd'T'HH:mm follows iso-standards, i.e html uses this format                                     
    private LocalDateTime dueDateTime; // OPTIONAL

    // default is always false in the constructors
    private boolean isCompleted;

    // default is always true in the constructors
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
        this.isCompleted = false;   // default is always false
        this.isPrivate = true;      // default is always true
        this.category = category;
        this.project = project;
        this.assignedUser = assignedUser;
    }


// if description, dueDateTime or project are not provided, they default to null
public Task(String title, boolean isCompleted, boolean isPrivate, Category category, AppUser assignedUser) {
    this.title = title;
    this.description = null;
    this.dueDateTime = null;
    this.isCompleted = false;   // default is always false
    this.isPrivate = true;      // default is always true;
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
