package hh.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.taskmanager.domain.AppUser;
import hh.taskmanager.domain.AppUserRepository;
import hh.taskmanager.domain.Project;
import hh.taskmanager.domain.ProjectRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest
public class ProjectRepositoryTests {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private AppUserRepository appUserRepository;
// ________________________________________________________________________________________________________________________________________________

    // CREATE a new project test
    @Test
    public void createNewProject() {
        AppUser user1 = new AppUser("test1", "test12345", "test1@hh.com", "USER", null, null);
        appUserRepository.save(user1);

        Project project = new Project("Test Project", "I gotta test this", true, user1);
        projectRepository.save(project);
        assertThat(project.getProjectId()).isNotNull();
        assertThat(project.getProjectId()).isNotNull();
        assertThat(project.getName().equals("Test Project"));
        assertThat(project.getDescription().equals("I gotta test this"));
        assertThat(project.getAssignedUser().getAppUserId().equals(user1.getAppUserId()));
    }

    // FIND project by name test
    @Test

    public void findByNameShouldReturnProject() {
        AppUser user2 = new AppUser("test2", "test12345", "test2@hh.com", "USER", null, null);
        appUserRepository.save(user2);
        
        Project project = new Project("Test Project", "I gotta test this", true, user2);
        projectRepository.save(project);
        Optional<Project> found = projectRepository.findByName("Test Project");
        assertThat(found).isNotNull();
        assertThat(found.get().getName().equals("Test Project"));
        assertThat(found.get().getDescription().equals("I gotta test this"));
        assertThat(found.get().getAssignedUser().getAppUserId().equals(user2.getAppUserId()));

    }

        // FIND project by id test
        @Test
        public void findByIdShouldReturnProject() {
            AppUser user3 = new AppUser("test3", "test12345", "test3@hh.com", "USER", null, null);
            appUserRepository.save(user3);

            Project project = new Project("Test Project", "I gotta test this", true, user3);
            projectRepository.save(project);
            Long projectId = project.getProjectId();
            Optional<Project> found = projectRepository.findById(projectId);
            assertThat(found).isNotNull();
            assertThat(found.get().getProjectId()).isEqualTo(projectId);
            assertThat(found.get().getName().equals("Test Project"));
            assertThat(found.get().getDescription().equals("I gotta test this"));
            assertThat(found.get().getAssignedUser().getAppUserId().equals(user3.getAppUserId()));
    }

    // DELETE project by Id test

    @Test
    public void deleteProjectById() {
        AppUser user4 = new AppUser("test4", "test12345", "test4@hh.com", "USER", null, null);
        appUserRepository.save(user4);

        Project project = new Project("Test Project", "I gotta test this", true, user4);
        projectRepository.save(project);
        Long projectId = project.getProjectId();
        projectRepository.deleteById(projectId);
        Optional<Project> deleted = projectRepository.findById(projectId);
        assertThat(deleted).isEmpty();
    }

}
