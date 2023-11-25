package sit.int222.nw1apisas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import sit.int222.nw1apisas.imp.CustomRepositoryImpl;
import sit.int222.nw1apisas.properties.FileStorageProperties;
import sit.int222.nw1apisas.properties.JwtProperties;


@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
@EnableConfigurationProperties({JwtProperties.class, FileStorageProperties.class})
@EnableScheduling
public class Nw1ApiSasApplication {

    public static void main(String[] args) {
        SpringApplication.run(Nw1ApiSasApplication.class, args);
    }

}
