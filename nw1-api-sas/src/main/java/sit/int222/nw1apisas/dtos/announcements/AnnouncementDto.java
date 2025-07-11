package sit.int222.nw1apisas.dtos.announcements;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int222.nw1apisas.dtos.categories.CategoryDto;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AnnouncementDto {
    private Integer id;
    private String announcementTitle;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private String announcementDisplay;
    @JsonIgnore
    private CategoryDto categoryId;
    private Integer viewCount;

    public String getAnnouncementCategory() {
        return categoryId.getCategoryName();
    }

}
