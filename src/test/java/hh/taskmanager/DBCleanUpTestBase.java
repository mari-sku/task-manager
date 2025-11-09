package hh.taskmanager;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

import hh.taskmanager.domain.AppUserRepository;
import hh.taskmanager.domain.ProjectRepository;
import hh.taskmanager.domain.TaskRepository;
import hh.taskmanager.domain.CategoryRepository;

// Base class for tests that need a clean database state. subclasses are the test classes (annotated with @SpringBootTest). they all extend this class..
// the class has to be abstract, so that spring won't run this class as a test. 
public abstract class DBCleanUpTestBase {

    @Autowired
    protected TaskRepository taskRepository;
    @Autowired
    protected ProjectRepository projectRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected AppUserRepository appUserRepository;

    @AfterEach  // after each test method in every class that inherits this class, this method will be called
    public void cleanupDatabase() {
        // delete in child -> parent order to avoid FK constraint problems. for example, i shouldn't delete project first and then task
        try { taskRepository.deleteAll(); } catch (Exception e) {}
        try { projectRepository.deleteAll(); } catch (Exception e) {}
        try { categoryRepository.deleteAll(); } catch (Exception e) {}
        try { appUserRepository.deleteAll(); } catch (Exception e) {}
    }
}
