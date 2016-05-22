package jp.co.ulsystems.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@SpringBootApplication
@EnableWebMvc
public class SpringBootAppApplication extends WebMvcAutoConfigurationAdapter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppApplication.class, args);
    }
    
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	super.addResourceHandlers(registry);
    	registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
