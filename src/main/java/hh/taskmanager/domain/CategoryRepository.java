package hh.taskmanager.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // to see how many tasks there are in a given category, see TaskRepository!!!

    // find category by name
    Optional<Category> findByName(String name);

    // OTHER POTENTIALLY USEFUL METHODS

    // search category by name (with a keyword)
    List<Category> findByNameContainingIgnoreCase(String keyword);

    // check if category name already exists (prevents duplicates)
    boolean existsByName(String name);

    // check how many categories there are
    long count();

    // categories without tasks / categories with tasks
    Iterable<Category> findByTasksIsEmpty();

    Iterable<Category> findByTasksIsNotEmpty();

    // show categories in alphabetical order
    Iterable<Category> findAllByOrderByNameAsc();

}
