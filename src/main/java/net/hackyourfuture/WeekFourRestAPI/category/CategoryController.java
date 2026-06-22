package net.hackyourfuture.WeekFourRestAPI.category;

import net.hackyourfuture.WeekFourRestAPI.category.dto.request.CreateCategoryRequest;
import net.hackyourfuture.WeekFourRestAPI.category.models.Category;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Validated  // ← Add this annotation
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {
  private final List<Category> categories = new ArrayList<>(List.of(
      new Category(1L, "Clothes", "https://i.imgur.com/QkIa5tT.jpeg"),
      new Category(2L, "Electronics", "https://i.imgur.com/ZANVnHE.jpeg"),
      new Category(3L, "Furniture", "https://i.imgur.com/Qphac99.jpeg")));

  // @GetMapping
  // public List<Category> getAllCategories() {
  // return categories;
  // }

  // @GetMapping("/{id}")
  // public Category getCategoryById(@PathVariable Long id) {
  // return categories.stream()
  // .filter(Objects::nonNull) // Remove any null
  // .filter(category -> id.equals(category.getId()))
  // .findFirst() // Get an Optional<Category> for the first matching Category
  // object
  // .orElse(null); // Unwrap Optional<Category> into Category by handling when
  // nothing is found
  // }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(
      @PathVariable @Positive(message = "ID must be a positive number") Long id) {
    return categories.stream()
        .filter(category -> id.equals(category.getId()))
        .findFirst()
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // @GetMapping
  // public List<Category> getAllCategories(@RequestParam(required = false) String
  // name) {
  // if (name == null || name.isBlank()) {
  // return categories;
  // }

  // return categories.stream()
  // .filter(category ->
  // category.getName().toLowerCase().contains(name.toLowerCase()))
  // .toList();
  // }categories

  @GetMapping
  public ResponseEntity<List<Category>> getAllCategories(
      @RequestParam(required = false) @Size(max = 50, message = "Category name filter must not exceed 50 characters") String name) {
    if (name == null || name.isBlank()) {
      return ResponseEntity.ok(categories);
    }

    List<Category> filteredList = categories.stream()
        .filter(category -> category.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();

    return ResponseEntity.ok(filteredList);
  }

  // @PostMapping
  // public Category createCategory(@RequestBody Category category) {
  // long nextId = categories.stream()
  // .map(Category::getId) // Call getId() on each Category type object to map
  // each to id
  // .max(Comparator.naturalOrder()) // Get natural sorting order comparison
  // algorithm for a,b and find max.
  // .orElse(0L) + 1; // Increment needed for when there's an id, then we need to
  // iterate 1 over max.

  // category.setId(nextId);
  // categories.add(category);

  // return category;
  // }
  @PostMapping
  public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
    long nextId = categories.stream()
        .map(Category::getId)
        .max(Comparator.naturalOrder())
        .orElse(0L) + 1;

    Category category = new Category(nextId, request.name(), request.image());
    categories.add(category);

    URI location = URI.create("/api/v1/categories/" + nextId);
    return ResponseEntity.created(location).body(category);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Category> replaceCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
    for (int i = 0; i < categories.size(); i++) {
      if (id.equals(categories.get(i).getId())) {
        updatedCategory.setId(id); // Make sure ID is set in Category as we get it without
        categories.set(i, updatedCategory);

        return ResponseEntity.ok(updatedCategory);
      }
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    boolean isRemoved = categories.removeIf(category -> id.equals(category.getId()));
    return isRemoved ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

}
