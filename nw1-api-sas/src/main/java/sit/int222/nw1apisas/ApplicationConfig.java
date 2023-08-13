package sit.int222.nw1apisas;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

}