package jp.co.ulsystems.sb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import jp.co.ulsystems.sb.auth.User;
import jp.co.ulsystems.sb.auth.UserRepository;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration(exclude=SpringBootAppApplication.class)
@Configuration
public class SpringBootAppApplication extends WebMvcAutoConfigurationAdapter implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppApplication.class, args);
    }
    
    @Autowired
    private UserRepository repo;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Override
    @Transactional
    public void run(String... arg0) throws Exception {
    	
    	User user = repo.findOne("user1");
    	if (user == null) {
    		user = new User();
    		user.setId("user1");
    		user.setHashPassword(encoder.encode("user1"));
    		repo.saveAndFlush(user);
    	} else {
    		user.setHashPassword(encoder.encode("user1"));
    	}
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

    @Bean
    public PasswordEncoder getPasswordEncorder() {
    	return new StandardPasswordEncoder();
    }
}
