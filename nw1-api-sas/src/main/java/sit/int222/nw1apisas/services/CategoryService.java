package sit.int222.nw1apisas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int222.nw1apisas.entities.Category;
import sit.int222.nw1apisas.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryById(Integer categoryId) {
        if (categoryId == null || !(categoryId instanceof Integer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid categoryId");
        }
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category id" + categoryId + "does not exist"));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createNewCategory(Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().trim() == "" || category.getCategoryName().length() > 50) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a category name that must less than or equal 50 characters");
        }
        Category newCategory = new Category();
        newCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.saveAndFlush(newCategory);
    }


}
