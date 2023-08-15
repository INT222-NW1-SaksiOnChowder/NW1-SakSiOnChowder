package sit.int222.nw1apisas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import sit.int222.nw1apisas.properties.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        JwtProperties.class
})
public class Nw1ApiSasApplication {

    public static void main(String[] args) {
        SpringApplication.run(Nw1ApiSasApplication.class, args);
    }

}
