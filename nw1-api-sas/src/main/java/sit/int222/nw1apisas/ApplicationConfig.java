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

//    @Bean
//    public Argon2PasswordEncoder argon2PasswordEncoder() {
//        return new Argon2PasswordEncoder(
//                16,
//                16,
//                1,
//                4096,
//                3
//        );
//    }

}