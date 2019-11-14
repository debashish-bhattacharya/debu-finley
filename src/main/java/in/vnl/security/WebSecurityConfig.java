package in.vnl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import in.vnl.repository.UsersService;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    UsersService userDetailsService;
 
    @Bean
    public CustomPasswordEnconder passwordEncoder(){
        return new CustomPasswordEnconder();
    }
     
     
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
 
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    	auth.userDetailsService(userDetailsService);
 
    }
    
  
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
        
    	
        	
    	
    	
    	/*http.csrf().disable();
 
        // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
 
        // /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
        // If no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers("/dashboard").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
 
        // For ADMIN only.
        http.authorizeRequests().antMatchers("/operation").access("hasRole('ROLE_ADMIN')");
 
        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        
        
        
        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/authenticate") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/dashboard")//
                .failureUrl("/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout");
*/        
        
    	//http.csrf().disable();
       // http.authorizeRequests().antMatchers("/dashboard").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')").and().formLogin();
       // http.authorizeRequests().antMatchers("/operation").access("hasRole('ROLE_ADMIN')").and().formLogin();
    	
    	//http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
    	 http.csrf().disable();
    	http.authorizeRequests().antMatchers("/","/css/**","/js/**","/images/**", "/login", "/logout").permitAll();
    	http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
    	
    	http.formLogin()//
        // Submit URL of login page.
        .loginProcessingUrl("/authenticate") // Submit URL
        .loginPage("/login")//
        .defaultSuccessUrl("/dashboard")//
        .failureUrl("/login?error=true")//
        .usernameParameter("username")//
        .passwordParameter("password")
        // Config for Logout Page
        .and().logout().logoutUrl("/logout");
 
    }
}