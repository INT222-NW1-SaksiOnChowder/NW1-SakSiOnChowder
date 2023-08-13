package sit.int222.nw1apisas.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ActiveAnnouncementDto {
    private Integer id;
    private String announcementTitle;

    @JsonIgnore
    private CategoryDto categoryId;

    public String getAnnouncementCategory(){
        return categoryId.getCategoryName();
    }

}
