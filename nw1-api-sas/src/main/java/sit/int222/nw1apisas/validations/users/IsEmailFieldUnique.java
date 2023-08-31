package sit.int222.nw1apisas.validations.users;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailFieldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsEmailFieldUnique {
    String message() default "email has already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
