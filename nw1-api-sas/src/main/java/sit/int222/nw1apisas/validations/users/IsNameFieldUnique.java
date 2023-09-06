package sit.int222.nw1apisas.validations.users;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameFieldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsNameFieldUnique {
    String message() default "does not unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
