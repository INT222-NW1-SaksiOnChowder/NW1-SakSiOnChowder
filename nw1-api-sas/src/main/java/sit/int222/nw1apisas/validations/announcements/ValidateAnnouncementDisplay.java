package sit.int222.nw1apisas.validations.announcements;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AnnouncementDisplayValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateAnnouncementDisplay {
    String message() default "must be either 'Y' or 'N'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}