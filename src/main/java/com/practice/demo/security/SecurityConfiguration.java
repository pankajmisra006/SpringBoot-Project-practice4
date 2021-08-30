package com.practice.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.practice.demo.service.UserPrincipalDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	UserPrincipalDetailService userPrincipalDetailService;
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
//		
//		 auth .inMemoryAuthentication() 
//		  .withUser("admin").password(passwordEncoder().encode("123welcome")).roles("ADMIN") 
//		  .and() 
//		  .withUser("punkk").password(passwordEncoder().encode("welcome")).roles("USER") .and()
//		  .withUser("manager").password(passwordEncoder().encode("welcome")).
//		  roles("MANAGER").authorities("ACCESS_TEST1"); //here you are assigning  authority to manager
			 
		 
		
        auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
       http
           .authorizeRequests()
           //.antMatchers("/").authenticated()
           .antMatchers("/profile/**").authenticated()  // ** is for all the controller inside profile
           .antMatchers("/admin/**").hasRole("ADMIN")
           .antMatchers("/management/**").hasAnyRole("ADMIN","MANAGER")
           .antMatchers("/api/public/test1").hasAuthority("ACCESS_TEST1")  //here you telling that this test1 will be
           .antMatchers("/api/public/test2").hasAuthority("ACCESS_TEST2")                                                          //accessed by those having  access_test1 authorties and in this case its manager
           .and()
           //.httpBasic(); // for pop-up 
           .formLogin() // for custome login form
           .loginPage("/login").permitAll()
           .and()
           .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
           //.remeberme() can be used for remember me feature.
           // .loginprocessingurl("/signin")   //here we can remove the default /login to /signin if we want
           // .usernameparameter("txtusername")  // we can also change the name variable based on our form
       
       
       
           
	}
	
	  @Bean
	    DaoAuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailService);

	        return daoAuthenticationProvider;
	    }
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder() ; 

}
	

}
