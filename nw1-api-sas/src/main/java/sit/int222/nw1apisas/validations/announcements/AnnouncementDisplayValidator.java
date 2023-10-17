package sit.int222.nw1apisas.validations.announcements;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AnnouncementDisplayValidator implements ConstraintValidator<ValidateAnnouncementDisplay, String> {
    @Override
    public boolean isValid(String announcementDisplay, ConstraintValidatorContext context) {
        return announcementDisplay == null || announcementDisplay.equals("Y") || announcementDisplay.equals("N");
    }
}