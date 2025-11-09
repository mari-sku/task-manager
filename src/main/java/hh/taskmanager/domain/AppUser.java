package hh.taskmanager.domain;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Pattern;
// import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

// Class name is AppUser, because 'User' is already a class in Spring Security

@Entity(name = "users") // 'user' is a reserved keyword in SQL databases, so I'll use 'users' instead
public class AppUser {

    // validations are set, so in the future i can have a safer 'register' page

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long appUserId;

    @NotBlank(message = "A unique username is required")
    @Column(name = "username", nullable = false, unique = true, updatable = false) // usernames must be unique
    private String username;

    // VALIDATIONS WILL BE IMPLEMENTED LATER, BECAUSE TEST DATA DOES NOT MEET THEM
    // @Size(min = 8, message = "Password must be at least 8 characters")
    // @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
    //      message = "Password must contain an uppercase letter, a number, and a special character")
    // @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "A unique email address is required")
    @Column(name = "email", nullable = false, unique = true) // emails must be unique
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "assignedUser")
    @JsonIgnoreProperties("assignedUser") // to avoid infinite loop during JSON serialization
    private List<Task> tasks;

    @OneToMany(mappedBy = "assignedUser")
    @JsonIgnoreProperties("assignedUser") // to avoid infinite loop during JSON serialization
    private List<Project> projects;


    // Constructors
    public AppUser() {
    }

    public AppUser(String username, String password, String email, String role, List<Task> tasks,
            List<Project> projects) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.tasks = new ArrayList<>(); //empty list
        this.projects = new ArrayList<>(); // empty list
    }

    // constructor without tasks and projects (empty list)
    
    public AppUser(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    // Getters and setters

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    // To string

    @Override
    public String toString() {
        return "AppUser [username=" + username + ", password=" + password + ", email=" + email + ", role=" + role + "]";
    }

}
