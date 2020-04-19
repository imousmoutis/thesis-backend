package gr.ioannis.thesis.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories({"com.eurodyn.qlack.fuse.aaa.repository"})
@EntityScan({"com.eurodyn.qlack.fuse.aaa.model"})
@ComponentScan(basePackages = {
    "com.eurodyn.qlack.fuse.aaa",
    "com.eurodyn.qlack.fuse.security",
    "gr.ioannis.thesis"
})
public class ThesisWebApplication {

  @Value("${qlack.fuse.security.cors.domains:http://localhost:3000}")
  private String secureDomains;

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(ThesisWebApplication.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
  }

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(Collections.singletonList(secureDomains));
    config.setAllowedHeaders(
        Arrays.asList("Authorization", "Origin", "Content-Type", "Accept"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
    config.addExposedHeader("Authorization");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

}
