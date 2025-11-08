package hh.taskmanager;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import hh.taskmanager.domain.*;


@SpringBootApplication
public class TaskmanagerApplication {

	// logger
	private static final Logger Log = LoggerFactory.getLogger(TaskmanagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}
@Bean
	public CommandLineRunner seedData(TaskRepository taskRepository, CategoryRepository categoryRepository, ProjectRepository projectRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {

			Log.info("------Mock-data: admin and user accounts created------");
			AppUser user = new AppUser("user", passwordEncoder.encode("user"), "user@hh.com", "USER", null, null);
			AppUser user1 = new AppUser("user1", passwordEncoder.encode("user1"), "user1@hh.com", "USER", null, null);
			AppUser admin = new AppUser("admin", passwordEncoder.encode("admin"), "admin@hh.com", "ADMIN", null, null);
			appUserRepository.save(user);
			appUserRepository.save(user1);
			appUserRepository.save(admin);

			Log.info("------Mock-data: categories created------");
			Category other = categoryRepository.save(new Category("Other", null));
			Category work = categoryRepository.save(new Category("Work", null));
			Category errands = categoryRepository.save(new Category("Errands", "Tasks to do on Wednesdays"));
			Category personal = categoryRepository.save(new Category("Personal", null));

			Log.info("------Mock-data: tasks without projects created------"); // dueDateTime format: yyyy-MM-dd'T'HH:mm
			taskRepository.save(new Task("Go to the shop", null, LocalDateTime.parse("2026-02-01T12:00"), false, true, errands, null, user));
			taskRepository.save(new Task("Meet up with Lisa", null, LocalDateTime.parse("2026-03-11T15:10"), false, true, personal, null, user));
			taskRepository.save(new Task("Doctor's appointment", "Ask for a new prescription", null, false, true, other, null, user));
			taskRepository.save(new Task("Buy milk", "Buy 2L milk", LocalDateTime.parse("2026-02-01T12:00"), false, true, errands, null, user));
			taskRepository.save(new Task("Meeting with John", "Show the application documents", LocalDateTime.parse("2026-04-05T14:15"), false, true, work, null, user));

			Log.info("------ Mock-data: projects created ------");

			Project taskManagerApp = projectRepository.save(new Project("TaskManager", "My Spring Boot application", false, user));
			Project diyPorch = projectRepository.save(new Project("DIY Porch", "Build a porch for the backyard",false, user));
			Project holidayPlanning = projectRepository.save(new Project("Holiday Planning", null, true, user));

			Log.info("------ Mock-data: tasks with projects created ------");
			taskRepository.save(new Task("Make test data for TaskManager", "The data will be used to test the application", LocalDateTime.parse("2026-01-15T08:00"), false,  true, work, taskManagerApp, user));
			taskRepository.save(new Task("Go to the beach", "There is a beach in Margate that we'd like to go to!", null, false, true, other, holidayPlanning, user));
			taskRepository.save(new Task("Buy wood and nails", null, LocalDateTime.parse("2026-01-15T10:00"), false, true, errands, diyPorch, user));
			taskRepository.save(new Task("Get paint and brush", "From Mickie's paint shop", LocalDateTime.parse("2026-01-15T08:00"), false, true, errands, diyPorch, user));
		};
	}
}
