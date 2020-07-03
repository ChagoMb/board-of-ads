package com.avito.controllers.rest;

import com.avito.models.Category;
import com.avito.service.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rest/categories")
public class CategoryRestController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRestController.class);

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> create(Category category) {
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(category));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getListOfCategory() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/root_categories")
    public ResponseEntity<List<Category>> getRootCategories() {
        return ResponseEntity.ok(categoryService.getRootCategories());
    }
    @GetMapping("/getCategoriesByParentCategory/{id}")
    public ResponseEntity<List<Category>> getCategoriesByParentCategory(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);
        List<Category> categoriesByParentCategory = categoryService.getCategoriesByParentCategory(category);
        return ResponseEntity.ok(categoriesByParentCategory);
    }
//    @GetMapping("/getCategoryById/{id}")
//    public ResponseEntity<Category> getCategoryById( ) {
//        return ResponseEntity.ok(categoryService.getCategoryById(name));
//    }
}
