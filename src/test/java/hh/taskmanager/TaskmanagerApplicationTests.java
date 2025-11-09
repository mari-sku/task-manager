package hh.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.security.test.context.support.WithMockUser;

import hh.taskmanager.web.*;

import static org.assertj.core.api.Assertions.assertThat;
import hh.taskmanager.domain.AppUserRepository;
import hh.taskmanager.domain.ProjectRepository;
import hh.taskmanager.domain.TaskRepository;
import hh.taskmanager.domain.CategoryRepository;
import hh.taskmanager.domain.AppUser;
import hh.taskmanager.domain.Project;
import hh.taskmanager.domain.Task;
import hh.taskmanager.domain.Category;

@SpringBootTest // this annotation tells that the entire application will be started for the test
@AutoConfigureMockMvc
@WithMockUser  // needed so that we can test as a logged in user. otherwise endpoints lead to /login
// @Transactional
public class TaskmanagerApplicationTests extends DBCleanUpTestBase {
	@Autowired
	private MockMvc mockMvc;

	// Autowire the controllers to be tested. constructor injection cannot be use here

	@Autowired
	private AdminController adminController;
	@Autowired
	private AppUserRestController appUserRestController;
	@Autowired
	private HomeController homeController;
	@Autowired
	private ProjectController projectController;
	@Autowired
	private ProjectRestController projectRestController;
	@Autowired
	private TaskController taskController;
	@Autowired
	private TaskRestController taskRestController;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	// ________________________________________________________________________________________________________________________________________________

	@Test // this annotation defines a method to be tested
	void contextLoads() throws Exception {
		assertThat(adminController).isNotNull();
		assertThat(appUserRestController).isNotNull();
		assertThat(homeController).isNotNull();
		assertThat(projectController).isNotNull();
		assertThat(projectRestController).isNotNull();
		assertThat(taskController).isNotNull();
		assertThat(taskRestController).isNotNull();
	}

	@Test
	public void homePageMockMvcTest() throws Exception {
		this.mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("TaskManager")));
	}

	@Test
	public void addProjectPageLoads() throws Exception {
		this.mockMvc.perform(get("/addproject"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Add New Project")));
	}

	@Test
	public void addTaskPageLoads() throws Exception {
		this.mockMvc.perform(get("/addtask"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Add New Task")));
	}

	@Test
	public void editProjectPageLoads() throws Exception {
	// create a user and project so the edit page exists (edit page URL needs project id)
	AppUser user17 = new AppUser("user1", "user12345", "user1@hh.com", "USER");
	appUserRepository.save(user17);
	Project project = new Project("Project to edit", "description", false, user17);
	projectRepository.save(project);

	this.mockMvc.perform(get("/editproject/" + project.getProjectId()))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Edit Project")));
	}

	@Test
	public void editTaskPageLoads() throws Exception {
	// create user, category and task so the edit page exists (edit page URL needs task id)
	AppUser user2 = new AppUser("user2", "user12345", "user2@hh.com", "USER");
	appUserRepository.save(user2);
	Category category = new Category("Category1", "description");
	categoryRepository.save(category);
	Task task = new Task("Task to edit", false, true, category, user2);
	taskRepository.save(task);

	this.mockMvc.perform(get("/edittask/" + task.getTaskId()))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Edit Task")));
	}

	@Test
	public void unknownUrlReturns404() throws Exception {
		this.mockMvc.perform(get("/nowhere"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}
