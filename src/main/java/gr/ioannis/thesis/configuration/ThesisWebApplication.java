package gr.ioannis.thesis.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories({"com.eurodyn.qlack.fuse.aaa.repository", "com.eurodyn.qlack.fuse.lexicon.repository",
    "gr.ioannis.thesis.repository"})
@EntityScan({"com.eurodyn.qlack.fuse.aaa.model", "com.eurodyn.qlack.fuse.lexicon.model", "gr.ioannis.thesis"
    + ".model"})
@ComponentScan(basePackages = {
    "com.eurodyn.qlack.fuse.aaa",
    "com.eurodyn.qlack.fuse.security",
    "com.eurodyn.qlack.fuse.lexicon",
    "gr.ioannis.thesis"
})
public class ThesisWebApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(ThesisWebApplication.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
  }

  @Bean
  public FilterRegistrationBean corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.addExposedHeader("Authorization");
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(0);

    return bean;
  }

}
