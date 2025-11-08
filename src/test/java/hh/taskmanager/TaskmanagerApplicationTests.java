package hh.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.taskmanager.web.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // this annotation tells that the entire application will be started for the test
public class TaskmanagerApplicationTests {

	// Autowire the controllers to be tested. constructor injection cannot be used here

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

	@Test	// this annotation defines a method to be tested
	void contextLoads() throws Exception {
		assertThat(adminController).isNotNull();
		assertThat(appUserRestController).isNotNull();
		assertThat(homeController).isNotNull();
		assertThat(projectController).isNotNull();
		assertThat(projectRestController).isNotNull();
		assertThat(taskController).isNotNull();
		assertThat(taskRestController).isNotNull();
	}

}
