package sit.int222.nw1apisas.dtos.announcements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int222.nw1apisas.dtos.categories.CategoryDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ActiveAnnouncementDto {
    private Integer id;
    private String announcementTitle;

    @JsonIgnore
    private CategoryDto categoryId;

    public String getAnnouncementCategory() {
        return categoryId.getCategoryName();
    }

}
