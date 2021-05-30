package com.example.springdata.configuration;

import com.example.springdata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // enable web security
@EnableGlobalMethodSecurity(prePostEnabled = true) // to secure separate methods
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private  DataSource dataSource; //application.properties
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
//    needed for jdbc auth
//    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }


//    --------------------------------------------------------------------------------------------------
    // 1 VARIANT
    // if we want to get data from  DB we need to use it
    // we need pom.xml injection below for that variant
//    <dependency>
//			<groupId>org.springframework.boot</groupId>
//			<artifactId>spring-boot-starter-jdbc</artifactId>
//		</dependency>`

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource);
//    }
    //    --------------------------------------------------------------------------------------------------

//    // 2 VARIANT IN MEMORY AUTH
    // IF U DONT WANT TO CONNECT TO DB(TEST SMTH)
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser(users.username("user1").password("pass1").roles("USER","ADMIN"))
//                .withUser(users.username("user2").password("pass2").roles("USER"));
//
//    }
//    --------------------------------------------------------------------------------------------------

    // ACCESS RULES

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//here we write what kind of requests we need  to secure
//                .anyRequest().permitAll()//любые запросы в нашем приложении доступны абсолютно всем
//                .antMatchers("/products/add/**").hasAnyRole("ADMIN")
                .antMatchers("/products/show/**").hasAnyRole("ADMIN")
                .anyRequest().permitAll()
//                .antMatchers("/products/**").hasAnyRole("ADMIN")
                //  /secured, мы можем его защитить (ЧТОБ ЗАЙТИ В ЭТОТ БЛОК НУЖНЫ ПРАВА АДМИНА)
                .and()
                .formLogin() // применяем свою форму
//                .httpBasic();
                .loginPage("/login")// сюда перекинет юзера после клика
                .loginProcessingUrl("/authenticateTheUser") // по этому адресу ссылается пост запрос сюда
                .permitAll();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //говорим что хотим использовать собственную аутентфикацию
        auth.authenticationProvider(authenticationProvider());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider  auth = new DaoAuthenticationProvider();
        //используй наш собственный userservice для работы с пользователем
        auth.setUserDetailsService(userService);
        // все пароли в базе даненых должны храниться в bcrypt hash
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}