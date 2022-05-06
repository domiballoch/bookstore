package bookstore.controller.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("Bookstore API")
                        .description("A personal project and sandpit for dev proof of concepts")
                        .version("0.0.1-SNAPSHOT")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
