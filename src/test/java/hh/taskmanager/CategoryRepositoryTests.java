package hh.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.taskmanager.domain.Category;
import hh.taskmanager.domain.CategoryRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest
public class CategoryRepositoryTests {

// autowire the repository to be tested. constructor injection cannot be used here
@Autowired
private CategoryRepository categoryRepository;

// ________________________________________________________________________________________________________________________________________________

// CREATE a new category test
@Test
public void createNewCategory() {
    Category category = new Category("Test Category", "Testing category creation");
    categoryRepository.save(category);
    assertThat(category.getCategoryId()).isNotNull();
    assertThat(category.getName().equals("Test Category"));
    assertThat(category.getDescription().equals("Testing category creation"));
}

// SEARCH category by name test
@Test
public void findByNameShouldReturnCategory() {
    Category category = new Category("Test Category", "Testing category search");
    categoryRepository.save(category);
    Optional<Category> found = categoryRepository.findByName("Test Category");
    assertThat(found).isNotNull();
    assertThat(found.get().getName().equals("Test Category"));
    assertThat(found.get().getDescription().equals("Testing category search"));
}

// SEARCH category by id test
@Test
public void findByIdShouldReturnCategory() {
    Category category = new Category("Test Category", "Testing category search by id");
    categoryRepository.save(category);
    Long categoryId = category.getCategoryId();
    Optional<Category> found = categoryRepository.findById(categoryId);
    assertThat(found).isNotNull();
    assertThat(found.get().getCategoryId()).isEqualTo(categoryId);
    assertThat(found.get().getName().equals("Test Category"));
    assertThat(found.get().getDescription().equals("Testing category search by id"));
}

// DELETE category by Id test

@Test
public void deleteCategoryById() {
    Category category = new Category("Test Category", "Testing category deletion");
    categoryRepository.save(category);
    Long categoryId = category.getCategoryId();
    categoryRepository.deleteById(categoryId);
    Optional<Category> deleted = categoryRepository.findById(categoryId);
    assertThat(deleted).isEmpty();

}
}
