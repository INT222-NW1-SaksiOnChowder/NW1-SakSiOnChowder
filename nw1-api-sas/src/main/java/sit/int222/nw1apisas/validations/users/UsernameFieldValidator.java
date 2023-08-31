package sit.int222.nw1apisas.validations.users;

import jakarta.validation.ConstraintValidator;
;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int222.nw1apisas.repositories.UserRepository;

public class UsernameFieldValidator implements ConstraintValidator<IsUsernameFieldUnique, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
       return !userRepository.existsUserByUsername(username.trim());
    }
}
