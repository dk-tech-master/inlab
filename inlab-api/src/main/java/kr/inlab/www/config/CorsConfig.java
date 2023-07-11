package kr.inlab.www.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    public static final String URL = "http://127.0.0.1:5173";
    public static final String URL2 = "http://localhost:5173";
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(URL, URL2)
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("*");
    }
}