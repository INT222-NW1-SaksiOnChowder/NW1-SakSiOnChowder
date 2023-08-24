package sit.int222.nw1apisas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sit.int222.nw1apisas.repositories.CustomRepositoryImpl;


@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class Nw1ApiSasApplication {

    public static void main(String[] args) {
        SpringApplication.run(Nw1ApiSasApplication.class, args);
    }

}
