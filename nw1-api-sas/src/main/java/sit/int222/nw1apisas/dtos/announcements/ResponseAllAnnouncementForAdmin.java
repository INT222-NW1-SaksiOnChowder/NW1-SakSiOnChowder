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
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAllAnnouncementForAdmin {
    private Integer id;
    private String announcementTitle;
    private String announcementDescription;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private String announcementDisplay;
    private Integer viewCount;
    private User announcementOwner;
    @JsonIgnore
    private CategoryDto categoryId;

    public String getAnnouncementOwner(){
        return announcementOwner.getUsername();
    }
    public String getAnnouncementCategory() {
        return categoryId.getCategoryName();
    }
}
