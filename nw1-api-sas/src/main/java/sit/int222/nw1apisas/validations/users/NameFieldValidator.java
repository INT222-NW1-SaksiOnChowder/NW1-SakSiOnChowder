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
//        if (name == null) {
//            return true;
//        }
//
//        String trimmedName = name.trim();
//        String lowercaseName = name.toLowerCase().trim();
//
//        if (!trimmedName.equals(name)) {
//            // If there are leading or trailing whitespaces
//            return !userRepository.existsUserByName(trimmedName) && !userRepository.existsUserByName(lowercaseName);
//        } else if (!lowercaseName.equals(name)) {
//            // If there are changes from uppercase to lowercase
//            return !userRepository.existsUserByName(trimmedName) && !userRepository.existsUserByName(lowercaseName);
//        } else {
//            // No changes in case, directly check the repository
//            return !userRepository.existsUserByName(trimmedName);
//        }
    }
}
