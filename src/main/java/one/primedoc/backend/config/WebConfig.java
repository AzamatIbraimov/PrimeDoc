package one.primedoc.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${category.image.upload.path}")
    String categoryUploadPath;

    @Value("${payment.image.upload.path}")
    String paymentUploadPath;

    @Value("${client.image.upload.path}")
    String clientUploadPath;

    @Value("${doctor.image.upload.path}")
    String doctorUploadPath;

    @Value("${bill.html.upload.path}")
    String billUploadPath;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api/v1", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**", "/bill/**")
                .addResourceLocations("file:" + categoryUploadPath,
                        "file:" + paymentUploadPath,
                        "file:" + clientUploadPath,
                        "file:" + doctorUploadPath,
                        "file:" + billUploadPath);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedOrigins("*");
            }
        };
    }
}