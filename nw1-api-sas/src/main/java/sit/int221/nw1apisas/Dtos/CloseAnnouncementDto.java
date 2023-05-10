package sit.int221.nw1apisas.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CloseAnnouncementDto {
    private Integer id;
    private String announcementTitle;

    private ZonedDateTime closeDate;

    @JsonIgnore
    private CategoryDto categoryId;

    public String getAnnouncementCategory(){
        return categoryId.getCategoryName();
    }

}
