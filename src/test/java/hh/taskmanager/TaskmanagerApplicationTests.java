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

import hh.taskmanager.web.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // this annotation tells that the entire application will be started for the
				// test
@AutoConfigureMockMvc
public class TaskmanagerApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	// Autowire the controllers to be tested. constructor injection cannot be used
	// here

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
		this.mockMvc.perform(get("/editproject"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Edit Project")));
	}

	@Test
	public void editTaskPageLoads() throws Exception {
		this.mockMvc.perform(get("/edittask"))
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
