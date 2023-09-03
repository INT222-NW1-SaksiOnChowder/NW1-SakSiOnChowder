package sit.int222.nw1apisas;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import sit.int222.nw1apisas.utils.ListMapper;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ListMapper listMapper() {
        return ListMapper.getInstance();
    }

    @Bean
    public Argon2PasswordEncoder argon2PasswordEncoder() {
        return new Argon2PasswordEncoder(
                16, // Memory cost in KB
                32, // Hash output length in bytes
                1,  // Parallelism factor (number of threads)
                60000, // Iterations (time cost)
                10   // Salt length in bytes
        );
    }

}