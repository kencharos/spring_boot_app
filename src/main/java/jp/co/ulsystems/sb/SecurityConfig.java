package jp.co.ulsystems.sb;

import org.apache.activemq.filter.function.makeListFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

//TODO 今は使用しない。
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final byte[] defaultSalt = "default".getBytes();
    
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()// TODO 今はアクセス制御を特に行わない。
                .antMatchers("/", "/css/**","/js/**", "/webjars/**","/**").permitAll();
            //    .anyRequest().authenticated()
            //    .and()
           // .formLogin()
           //     .loginPage("/login")
           //     .permitAll()
            //    .and()
           // .logout()
           //     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
           //     .permitAll();
        // CSRF対策も一旦無効
        RequestMatcher matcher = request -> !request.getRequestURI().startsWith("/");
        http.csrf().requireCsrfProtectionMatcher(matcher);
    }

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(new StandardPasswordEncoder());
    }
    
}