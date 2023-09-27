package org.zerock.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        log.info("-------------------userDital------------------------");
        log.info("데이터소스: {}", dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM user_list WHERE name = ?";
        String username = "admin";
        Object[] params = new Object[]{username};
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, params);
        System.out.println(result);
        System.out.println(result.get(0).get("name").toString());

        UserDetails user = User.builder()
                .username(result.get(0).get("name").toString())
                .password(result.get(0).get("password").toString())
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("-------------------filterChain------------------------");
//        log.info("데이터소스: {}", dataSource);
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        String sql = "SELECT * FROM user_list WHERE name = ?";
//        String username = "user";
//        Object[] params = new Object[]{username};
//        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, params);
//        System.out.println(result);

        http.authorizeHttpRequests()
                .antMatchers("/all/**").permitAll()
                .antMatchers("/member/**").hasRole("USER");
        http.formLogin();
        http.csrf().disable();
        http.logout();
        return http.build();
    }

}
