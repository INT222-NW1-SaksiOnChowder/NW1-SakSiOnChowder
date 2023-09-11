package sit.int222.nw1apisas.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int222.nw1apisas.entities.Category;
import sit.int222.nw1apisas.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = {"http://ip22nw1.sit.kmutt.ac.th","http://intproj22.sit.kmutt.ac.th","https://intproj22.sit.kmutt.ac.th"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Category createNewCategory(@RequestBody Category category) {
        return categoryService.createNewCategory(category);
    }

}
