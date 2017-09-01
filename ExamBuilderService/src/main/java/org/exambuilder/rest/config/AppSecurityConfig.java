package org.exambuilder.rest.config;

import org.exambuilder.rest.security.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf().disable();
		
		http
			.authorizeRequests()
			.antMatchers("/login*").anonymous()
			.antMatchers("/user/registration").permitAll()
			.antMatchers("/createTest").hasAuthority("ADMIN")
			.anyRequest().authenticated();
		
		http
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/process_login")
			.successHandler(authenticationSuccessHandler())
//			.defaultSuccessUrl("/", false)
			.failureUrl("/login?error=true");
		
		http
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login");
	}

	@Bean
	public MyAuthenticationSuccessHandler authenticationSuccessHandler() {
		return new MyAuthenticationSuccessHandler();
	}
	
}
