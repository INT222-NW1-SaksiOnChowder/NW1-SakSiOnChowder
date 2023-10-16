package sit.int222.nw1apisas.dtos.announcements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int222.nw1apisas.dtos.categories.CategoryDto;
import sit.int222.nw1apisas.entities.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ActiveAnnouncementDetailDto {
    private Integer id;
    private String announcementTitle;

    private String announcementDescription;

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
