package hh.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.taskmanager.domain.AppUser;
import hh.taskmanager.domain.AppUserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest
public class AppUserRepositoryTests {

    // autowired injection for repository to be tested. constructor injection cannot be used here

    @Autowired
    private AppUserRepository appUserRepository;

// ________________________________________________________________________________________________________________________________________________

// password encoder couldnt be used in the tests properly, so plain text passwords are used here
                          
// CREATE new user test
@Test 
public void createNewUser() {
        AppUser user = new AppUser("test1", "test12345", "test1@hh.com", "USER", null, null);
        appUserRepository.save(user);
        assertThat(user.getAppUserId()).isNotNull();
        assertThat(user.getUsername()).isEqualTo("test1");
        assertThat(user.getPassword()).isEqualTo("test12345");
        assertThat(user.getEmail()).isEqualTo("test1@hh.com");
        assertThat(user.getRole()).isEqualTo("USER");
        assertThat(user.getTasks()).isNull();
        assertThat(user.getProjects()).isNull();
    }

// SEARCH user by username test
@Test
 public void findByUsernameShouldReturnUser() {
         AppUser user = new AppUser("test2", "test12345", "test2@hh.com", "USER", null, null);
        appUserRepository.save(user);
        AppUser found = appUserRepository.findByUsername("test2");
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("test2");
        assertThat(found.getEmail()).isEqualTo("test2@hh.com");
        assertThat(found.getRole()).isEqualTo("USER");
    }

    // SEARCH user by id test
@Test
 public void findByIdShouldReturnUser() {
         AppUser user = new AppUser("test3", "test12345", "test3@hh.com", "USER", null, null);
        appUserRepository.save(user);
        Optional<AppUser> found = appUserRepository.findById(user.getAppUserId());    
        assertThat(found).isNotNull();
        assertThat(found.get().getAppUserId()).isEqualTo(user.getAppUserId());
        assertThat(found.get().getUsername()).isEqualTo("test3");   // get() used to get to the Optional object's object AppUser, and then we can use its methods
        assertThat(found.get().getEmail()).isEqualTo("test3@hh.com");
        assertThat(found.get().getRole()).isEqualTo("USER");
    }

// DELETE user by id test
    @Test
    public void deleteUserShouldRemoveFromRepository() {
         AppUser user = new AppUser("test4", "test12345", "test4@hh.com", "USER", null, null);
        appUserRepository.save(user);
        Long id = user.getAppUserId();
        appUserRepository.deleteById(id);
        assertThat(appUserRepository.findById(id)).isEmpty();
    }

}
