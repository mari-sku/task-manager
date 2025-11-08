package hh.taskmanager.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Optional<Task> findByTitle(String title);
    List<Task> findByProjectIsNull();
}
