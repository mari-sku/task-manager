package hh.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.taskmanager.domain.Task;
import hh.taskmanager.domain.TaskRepository;
import hh.taskmanager.domain.Category;
import hh.taskmanager.domain.Project;
import hh.taskmanager.domain.AppUser;
import hh.taskmanager.domain.AppUserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TaskRepositoryTests {

    // autowire the repository to be tested. constructor injection cannot be used here
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AppUserRepository appUserRepository;

// ________________________________________________________________________________________________________________________________________________

    // CREATE a new task test
    @Test
    public void createNewTask() {
        AppUser user1 = new AppUser("test1", "test12345", "test1@hh.com", "USER", null, null);
        appUserRepository.save(user1);
        Category category = new Category("Test Category", null);
        Task task = new Task("Test Task", "Testing task creation", LocalDateTime.now(), false, true, category, null, user1);
        taskRepository.save(task);
        assertThat(task.getTaskId()).isNotNull();
        assertThat(task.getTitle().equals("Test Task"));
        assertThat(task.getDescription().equals("Testing task creation"));
        assertThat(task.getAssignedUser().getAppUserId().equals(user1.getAppUserId()));
    }

    // SEARCH task by title test
    @Test
    public void findByTitleShouldReturnTask() {
        AppUser user2 = new AppUser("test2", "test12345", "test2@hh.com", "USER", null, null);
        appUserRepository.save(user2);
        Category category = new Category("Test Category", null);
        Task task = new Task("Test Task", "Testing task search", LocalDateTime.now(), false, true, category, null, user2);
        taskRepository.save(task);
        Optional<Task> found = taskRepository.findByTitle("Test Task");
        assertThat(found).isNotNull();
        assertThat(found.get().getTitle().equals("Test Task"));
        assertThat(found.get().getDescription().equals("Testing task search"));
        assertThat(found.get().getAssignedUser().getAppUserId().equals(user2.getAppUserId()));
    }

    // SEARCH task by id test
    @Test
    public void findByIdShouldReturnTask() {
        AppUser user3 = new AppUser("test3", "test12345", "test3@hh.com", "USER", null, null);
        appUserRepository.save(user3);
        Category category = new Category("Test Category", null);
        Task task = new Task("Test Task", "Testing task search by id", LocalDateTime.now(), false, true, category, null, user3);
        taskRepository.save(task);
        Long taskId = task.getTaskId();
        Optional<Task> found = taskRepository.findById(taskId);
        assertThat(found).isNotNull();
        assertThat(found.get().getTaskId()).isEqualTo(taskId);
        assertThat(found.get().getTitle().equals("Test Task"));
        assertThat(found.get().getDescription().equals("Testing task search by id"));
        assertThat(found.get().getAssignedUser().getAppUserId().equals(user3.getAppUserId()));
    }

    // DELETE task by Id test
    @Test
    public void deleteTaskById() {
        AppUser user4 = new AppUser("test4", "test12345", "test4@hh.com", "USER", null, null);
        appUserRepository.save(user4);
        Category category = new Category("Test Category", null);
        Task task = new Task("Test Task", "Testing task deletion", LocalDateTime.now(), false, true, category, null, user4);
        taskRepository.save(task);
        Long taskId = task.getTaskId();
        taskRepository.deleteById(taskId);
        Optional<Task> deleted = taskRepository.findById(taskId);
        assertThat(deleted).isEmpty();
    }

    // FIND tasks with no project test
    @Test
    public void findByProjectIsNullShouldReturnUnassignedTasks() {
        AppUser user5 = new AppUser("test5", "test12345", "test5@hh.com", "USER", null, null);
        appUserRepository.save(user5);
        Category category = new Category("Test Category", null);
        Project project = new Project("Test Project", "Project Description", false, user5);

        // test data: task with project, task without project
        Task taskWithProject = new Task("Task With Project", "Task assigned to project", LocalDateTime.now(), false, true, category, project, user5);
        Task taskWithoutProject = new Task("Task Without Project", "Task not assigned to any project", LocalDateTime.now(), false, true, category, null, user5);
        
        taskRepository.save(taskWithProject);
        taskRepository.save(taskWithoutProject);

        List<Task> unassignedTasks = taskRepository.findByProjectIsNull();
        assertThat(unassignedTasks).isNotNull();
        assertThat(unassignedTasks).hasSize(1);
        assertThat(unassignedTasks.get(0).getTitle()).isEqualTo("Task Without Project");
    }
}
