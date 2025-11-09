package hh.taskmanager.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    // find project by name
    Optional<Project> findByName(String name);

    // OTHER POTENTIALLY USEFUL METHODS

    // find all projects of a given assigned user
    Iterable<Project> findByAssignedUser(AppUser user);

    // search project by name (with a keyword)
    Iterable<Project> findByNameContainingIgnoreCase(String keyword);

    // find private/public projects
    Iterable<Project> findByIsPrivateTrue();

    Iterable<Project> findByIsPrivateFalse();

    // all projects in alphabetical order
    Iterable<Project> findAllByOrderByNameAsc();

    // count existing projects
    long count();

    // count an existing users projects
    long countByAssignedUser(AppUser user);

    // projects without tasks / projects with tasks
    Iterable<Project> findByTasksIsEmpty();

    Iterable<Project> findByTasksIsNotEmpty();

}
