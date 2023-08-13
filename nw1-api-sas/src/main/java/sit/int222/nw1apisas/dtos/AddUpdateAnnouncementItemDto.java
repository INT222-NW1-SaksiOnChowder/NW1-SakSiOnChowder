package sit.int222.nw1apisas.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int222.nw1apisas.enums.AnnouncementDisplay;

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
    private AnnouncementDisplay announcementDisplay;
    private CategoryDto categoryId;
    public Integer getCategoryId(){
        return categoryId.getCategoryId();
    }
    public String getAnnouncementCategory() {
        return categoryId.getCategoryName();
    }


}
