package es.ariel.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.ariel.app.auth.handler.LoginSuccessHandler;

import es.ariel.app.models.service.JpaUserDetailService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	
	@Autowired 
	private JpaUserDetailService userDetailService;
	
	@Autowired
	public void configurerGlobal(  AuthenticationManagerBuilder builder) throws Exception {		
	
		builder.userDetailsService(userDetailService)
		.passwordEncoder(passwordEncoder);
		
	}	
  
	@Autowired
	private LoginSuccessHandler successHandler;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/","index","/home",  "/loginAnonimo","/css/**", "/js/**","/images/**").permitAll()
		.antMatchers("/ver/**", "/ranking","/eliminarPartidas/**", "/api/**",
                "/listar**", "/dados/**","/formDados/**","/editar/**", "/form/**").hasAnyRole("USER")		
		.antMatchers("/**").hasAnyRole("ADMIN")		
		.anyRequest().authenticated()
		.and()
			.formLogin()
			 .successHandler(successHandler)
			.loginPage("/login")
			.permitAll()
		.and()
			.logout().permitAll()
		.and()
		    .exceptionHandling().accessDeniedPage("/error_403");
		
	}

}

