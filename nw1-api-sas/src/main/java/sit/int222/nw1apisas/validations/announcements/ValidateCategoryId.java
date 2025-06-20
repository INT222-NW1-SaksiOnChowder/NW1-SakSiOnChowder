package sit.int222.nw1apisas.validations.announcements;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateCategoryId {
    String message() default "does not exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}