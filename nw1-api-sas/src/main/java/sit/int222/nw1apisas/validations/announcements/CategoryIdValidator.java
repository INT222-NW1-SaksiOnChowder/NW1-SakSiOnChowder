package sit.int222.nw1apisas.validations.announcements;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int222.nw1apisas.repositories.CategoryRepository;
import sit.int222.nw1apisas.validations.announcements.ValidateCategoryId;

public class CategoryIdValidator implements ConstraintValidator<ValidateCategoryId, Integer> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isValid(Integer categoryId, ConstraintValidatorContext context) {
        if (categoryId == null) {
            return true;
        } else {
            return categoryRepository.existsById(categoryId);
        }
    }
}
