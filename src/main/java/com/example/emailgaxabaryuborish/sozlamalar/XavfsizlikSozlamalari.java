package com.example.emailgaxabaryuborish.sozlamalar;

import com.example.emailgaxabaryuborish.service.UserService;
import com.example.emailgaxabaryuborish.token.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;
@Configuration
@EnableWebSecurity
public class XavfsizlikSozlamalari extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    Filter filter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth
               .userDetailsService(userService)
               .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/users/qoshish","/users/tasdiqlash","/users/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        http
                .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("axrorovakmal4@gmail.com");
        mailSender.setPassword("kyvynyplkzrwqvpw");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.debug","true");
        return mailSender;
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
