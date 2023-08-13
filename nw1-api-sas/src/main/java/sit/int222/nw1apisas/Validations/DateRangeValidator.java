package sit.int222.nw1apisas.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sit.int222.nw1apisas.Dtos.AnnouncementItemDto;

public class DateRangeValidator implements ConstraintValidator<closeDate, AnnouncementItemDto> {

    @Override
    public boolean isValid(AnnouncementItemDto dto, ConstraintValidatorContext context) {
        if (dto.getCloseDate()==null||dto.getPublishDate()==null){
            return true;
        }
        return dto.getPublishDate().isBefore(dto.getCloseDate());
    }
}
