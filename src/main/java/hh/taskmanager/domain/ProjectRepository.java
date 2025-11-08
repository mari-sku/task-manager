package hh.taskmanager.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
Optional<Project> findByName(String name);
}
