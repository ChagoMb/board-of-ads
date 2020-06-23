package com.avito.controllers.rest;

import com.avito.models.Category;
import com.avito.models.User;
import com.avito.service.interfaces.CategoryService;
import com.avito.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryRestController {

    private final CategoryService categoryService;

    @PutMapping("/add")
    public Category create(Category category) {
        return categoryService.addCategory(category);
    }

    @PostMapping("/edit")
    public Category update(Category category) {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
    }


}
