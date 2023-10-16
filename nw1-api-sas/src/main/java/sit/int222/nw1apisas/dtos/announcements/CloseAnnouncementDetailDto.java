package sit.int222.nw1apisas.dtos.announcements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int222.nw1apisas.dtos.categories.CategoryDto;
import sit.int222.nw1apisas.entities.User;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CloseAnnouncementDetailDto {
    private Integer id;
    private String announcementTitle;

    private String announcementDescription;

    private ZonedDateTime closeDate;

    @JsonIgnore
    private CategoryDto categoryId;
    private User announcementOwner;

    public String getAnnouncementCategory() {
        return categoryId.getCategoryName();
    }
    public String getAnnouncementOwner(){
        return announcementOwner.getUsername();
    }
}
