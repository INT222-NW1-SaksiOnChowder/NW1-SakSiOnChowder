package sit.int222.nw1apisas.validations.users;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int222.nw1apisas.repositories.UserRepository;


public class NameFieldValidator implements ConstraintValidator<IsNameFieldUnique, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if(name == null){
            return true;
        }
       return !userRepository.existsUserByName(name.trim());
    }
}
