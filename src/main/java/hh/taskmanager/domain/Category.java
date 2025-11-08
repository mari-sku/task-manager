package hh.taskmanager.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;
    
    @Column(nullable = false)
    private String name;

    private String description; // OPTIONAL

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category") // to avoid infinite loop during JSON serialization
    private List<Task> tasks; 


    // Constructors

    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.tasks = null;
    }

     public Category(String name, String description, List<Task> tasks) {
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    
    // Getters and Setters

   

    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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
    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

   

    // To String
   
 @Override
    public String toString() {
        return "Category [name=" + name + ", description=" + description + "]";
    }

    
}
