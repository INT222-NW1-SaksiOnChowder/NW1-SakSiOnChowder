package sit.int222.nw1apisas.validations.announcements;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface closeDate {
    String message() default "must be later than publish date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
