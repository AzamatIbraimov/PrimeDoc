package one.primedoc.backend.config;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("one.primedoc.backend.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(metadata());
    }

    private ApiInfo metadata(){
        return new ApiInfo("PrimeDoc REST API Documentation",
                "Some custom description of API.",
                "1.0 SNAPSHOT",
                "Terms of service",
                new Contact("Prime Clinic", "www.prime.kg", "primecdoctor@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}
