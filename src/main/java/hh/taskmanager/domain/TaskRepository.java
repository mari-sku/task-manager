package hh.taskmanager.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

    // find task by title
    Optional<Task> findByTitle(String title);

    // OTHER POTENTIALLY USEFUL METHODS

    // search task by title (with a keyword)
    Iterable<Task> findByTitleContainingIgnoreCase(String keyword);

    // find task by id
    Optional<Task> findById(long taskId);

    // find task by duedate
    Optional<Task> findByDueDateTime(LocalDateTime dueDateTime);

    // find all tasks of a given assigned user
    Iterable<Task> findByAssignedUser(AppUser user);

    // find tasks that aren't related to a project
    List<Task> findByProjectIsNull();

    // count how many tasks there are in the given category
    long countByCategory_Name(String categoryName);
}
