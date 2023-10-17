package sit.int222.nw1apisas.dtos.announcements;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int222.nw1apisas.dtos.categories.CategoryDto;
import sit.int222.nw1apisas.entities.Category;
import sit.int222.nw1apisas.entities.User;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUpdateAnnouncementItemDto {
    private Integer id;
    private String announcementTitle;
    private String announcementDescription;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private String announcementDisplay;
    private Category categoryId;
    private User announcementOwner;

    public Integer getCategoryId() {
        return categoryId.getCategoryId();
    }

    public String getAnnouncementCategory() {
        return categoryId.getCategoryName();
    }

    public String getAnnouncementOwner() {
        return announcementOwner.getUsername();
    }


}
