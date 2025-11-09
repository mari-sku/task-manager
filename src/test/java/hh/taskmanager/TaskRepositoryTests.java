package hh.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.taskmanager.domain.Task;
import hh.taskmanager.domain.TaskRepository;
import jakarta.transaction.Transactional;
import hh.taskmanager.domain.Category;
import hh.taskmanager.domain.CategoryRepository;
import hh.taskmanager.domain.AppUser;
import hh.taskmanager.domain.AppUserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@SpringBootTest
public class TaskRepositoryTests extends DBCleanUpTestBase {

    // autowire the repository to be tested. constructor injection cannot be used here
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private CategoryRepository categoryRepository;

// ________________________________________________________________________________________________________________________________________________

    // CREATE a new task test
    @Test
    public void createNewTask() {
        AppUser user1 = new AppUser("test1", "test12345", "test1@hh.com", "USER", null, null);
        appUserRepository.save(user1);
        Category category1 = new Category("Test Category1", null);
        categoryRepository.save(category1);
        Task task1 = new Task("Test Task1", "Testing task creation1", LocalDateTime.now().plusDays(1), false, true, category1, null, user1);
        taskRepository.save(task1);
        assertThat(task1.getTaskId()).isNotNull();
        assertThat(task1.getTitle().equals("Test Task§"));
        assertThat(task1.getDescription().equals("Testing task creation§"));
        assertThat(task1.getAssignedUser().getAppUserId().equals(user1.getAppUserId()));
    }

    // SEARCH task by title test
    @Test
    public void findByTitleShouldReturnTask() {
        AppUser user2 = new AppUser("test2", "test12345", "test2@hh.com", "USER", null, null);
        appUserRepository.save(user2);
        Category category2 = new Category("Test Category2", null);
        categoryRepository.save(category2);
        Task task2 = new Task("Test Task2", "Testing task search2", LocalDateTime.now().plusDays(1), false, true, category2, null, user2);
        taskRepository.save(task2);
        Optional<Task> found1 = taskRepository.findByTitle("Test Task2");
        assertThat(found1).isNotNull();
        assertThat(found1.get().getTitle().equals("Test Task2"));
        assertThat(found1.get().getDescription().equals("Testing task search2"));
        assertThat(found1.get().getAssignedUser().getAppUserId().equals(user2.getAppUserId()));
    }

    // SEARCH task by id test
    @Test
    public void findByIdShouldReturnTask() {
        AppUser user3 = new AppUser("test3", "test12345", "test3@hh.com", "USER", null, null);
        appUserRepository.save(user3);
        Category category3 = new Category("Test Category3", null);
        categoryRepository.save(category3);
        Task task3 = new Task("Test Task3", "Testing task search by id3", LocalDateTime.now().plusDays(1), false, true, category3, null, user3);
        taskRepository.save(task3);
        Long taskId = task3.getTaskId();
        Optional<Task> found2 = taskRepository.findById(taskId);
        assertThat(found2).isNotNull();
        assertThat(found2.get().getTaskId()).isEqualTo(taskId);
        assertThat(found2.get().getTitle().equals("Test Task3"));
        assertThat(found2.get().getDescription().equals("Testing task search by id3"));
        assertThat(found2.get().getAssignedUser().getAppUserId().equals(user3.getAppUserId()));
    }

    // DELETE task by Id test
    @Test
    public void deleteTaskById() {
        AppUser user4 = new AppUser("test4", "test12345", "test4@hh.com", "USER", null, null);
        appUserRepository.save(user4);
        Category category4 = new Category("Test Category4", null);
        categoryRepository.save(category4);
        Task task4 = new Task("Test Task4", "Testing task deletion4", LocalDateTime.now().plusDays(1), false, true, category4, null, user4);
        taskRepository.save(task4);
        Long taskId = task4.getTaskId();
        taskRepository.deleteById(taskId);
        Optional<Task> deleted = taskRepository.findById(taskId);
        assertThat(deleted).isEmpty();
    }

    // FIND tasks with no project test
// this one doesnt work yet, because it fectches all the mock data too (so hasSize(1) is never true)

    // @Test
    // public void findByProjectIsNullShouldReturnUnassignedTasks() {
    //     AppUser user5 = new AppUser("test5", "test12345", "test5@hh.com", "USER", null, null);
    //     appUserRepository.save(user5);
    //     Category category = new Category("Test Category5", null);
    //     categoryRepository.save(category);
    //     Project project = new Project("Test Project5", "Project Description5", false, user5);
    //     projectRepository.save(project);

    //     // test data: task with project, task without project
    //     Task taskWithProject = new Task("Task With Project", "Task assigned to project", LocalDateTime.now().plusDays(1), false, true, category, project, user5);
    //     taskRepository.save(taskWithProject);
    //     Task taskWithoutProject = new Task("Task Without Project", "Task not assigned to any project", LocalDateTime.now().plusDays(1), false, true, category, null, user5);
    //     taskRepository.save(taskWithoutProject);

    //     List<Task> unassignedTasks = taskRepository.findByProjectIsNull();
    //     assertThat(unassignedTasks).isNotNull();
    //     assertThat(unassignedTasks).hasSize(1);
    //     assertThat(unassignedTasks.get(0).getTitle()).isEqualTo("Task Without Project");
    // }
}
