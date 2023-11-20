package sit.int222.nw1apisas.dtos.subscriptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubRequest {
    private String email;
    private Integer categoryId;
}
