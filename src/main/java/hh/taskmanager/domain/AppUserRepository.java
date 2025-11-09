package hh.taskmanager.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    // this method will be used by Spring Security to load user details during authentication
    Optional<AppUser> findByUsername(String username);

    // OTHER POTENTIALLY USEFUL METHODS

    // search user by username or email (with a keyword)
    List<AppUser> findByUsernameContainingIgnoreCase(String keyword);

    List<AppUser> findByEmailContainingIgnoreCase(String keyword);

    // return users that have no tasks and no projects assigned
    List<AppUser> findByTasksIsEmptyAndProjectsIsEmpty();

    // return users with a specific role (user or admin)
    List<AppUser> findByRole(String role);

    // count the amount of users with a specific role (user or admin)
    long countByRole(String role);

    // users who have at least one project
    List<AppUser> findByProjectsIsNotEmpty();

    // users who have at least one task
    List<AppUser> findByTasksIsNotEmpty();

    // users who have both tasks and projects
    List<AppUser> findByTasksIsNotEmptyAndProjectsIsNotEmpty();

    // users with no projects but some tasks
    List<AppUser> findByProjectsIsEmptyAndTasksIsNotEmpty();

}
