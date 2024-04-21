package npl.ist.department.tsp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow only requests from 'http://localhost:4200'
        config.addAllowedOrigin("http://localhost:4200");

        // Allow credentials (cookies, authentication headers)
        config.setAllowCredentials(true);

        // Allow all headers and methods
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config); // Adjust the path to match your API endpoints

        return new CorsFilter(source);
    }
}
