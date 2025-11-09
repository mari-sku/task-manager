package hh.taskmanager.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hh.taskmanager.domain.AppUser;
import hh.taskmanager.domain.AppUserRepository;

@CrossOrigin
@RestController
public class AppUserRestController {

    private final AppUserRepository appUserRepository;

    public AppUserRestController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

// ________________________________________________________________________________________________________________________________________________

    // get all users
    @GetMapping("/api/users")
    public List<AppUser> getAllUsers() {
        return (List<AppUser>) appUserRepository.findAll();
    }

    // get user by id
    @GetMapping("/api/users/{id}")
    public Optional<AppUser> getUserById(@PathVariable("id") Long userId) {
        return appUserRepository.findById(userId);
    }

    // delete user by id
    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        appUserRepository.deleteById(userId);
    }

}
