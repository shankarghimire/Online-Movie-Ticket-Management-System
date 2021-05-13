package ca.sheriancollege.ghimirsh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	LoginAccessDeniedHandler loginAccessDeniedHandler;
	
	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		
		return daoAuthenticationProvider;
	}

	//Configure methods...
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.antMatchers("/admin/**").hasRole("ADMIN")
//		.antMatchers("/user/**").hasRole("USER")
//		.antMatchers("/**","/css/**","/js/**","/images/**").permitAll()
//		.antMatchers("/h2-console/**").permitAll()
//		.and()
//		.formLogin()
//		.and()
//		.csrf().disable();
		
		http.csrf().disable(); //Will be removed this feature while Deployment
		http.headers().frameOptions().disable(); //Will be removed this feature wile Deployment
		http
		.authorizeRequests()
		//
		.antMatchers("/admin/**","/h2-console/**").hasRole("ADMIN")
		//.antMatchers("/admin/**").hasAuthority("ADMIN")
		.antMatchers("/user/**").hasRole("USER")
		//.antMatchers("/user/**").hasAuthority("USER")
		.antMatchers("/","/order-ticket", "/confirm-tic","/confirm-ticket","/test","/print-ticket","/purchase-ticket","/register","/do-register","/movie","/process-ticket","/about-us","/contact-us","/js/**","/css/**","/images/**").permitAll()
		//.antMatchers("/h2-console/**").permitAll()//Will be removed while deployment		
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login").permitAll()
		.and()
		.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout")
			.permitAll()
		.and()
		.exceptionHandling()
		.accessDeniedHandler(loginAccessDeniedHandler);
		
	}
	
	
	
	
	
	
	
}
