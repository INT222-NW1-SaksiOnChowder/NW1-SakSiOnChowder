package sit.int222.nw1apisas.dtos.announcements;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int222.nw1apisas.validations.announcements.ValidateAnnouncementDisplay;
import sit.int222.nw1apisas.validations.announcements.ValidateCategoryId;
import sit.int222.nw1apisas.validations.announcements.closeDate;

import java.time.ZonedDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@closeDate

public class AnnouncementItemDto {
    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be blank")
    @Size(min = 1, max = 200, message = "size must be between 1 and 200")
    private String announcementTitle;
    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be blank")
    @Size(min = 1, max = 10000, message = "size must be between 1 and 10000")
    private String announcementDescription;
    @FutureOrPresent(message = "must be a date in the present or in the future")
    private ZonedDateTime publishDate;
    @Future(message = "must be a future date")
    private ZonedDateTime closeDate;
    @ValidateAnnouncementDisplay
    private String announcementDisplay;
    @NotNull(message = "must not be null")
    @ValidateCategoryId
    private Integer categoryId;

    private Integer announcementOwner;

    public String getAnnouncementDisplay() {
        if (announcementDisplay == null) {
            return announcementDisplay = "N";
        } else {
            return announcementDisplay;
        }
    }

}
