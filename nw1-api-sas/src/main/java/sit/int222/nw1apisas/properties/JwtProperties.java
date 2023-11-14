package sit.int222.nw1apisas.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private Integer refreshTokenExpired;
    private Integer accessTokenExpired;
    private Integer otpTokenExpired;
}