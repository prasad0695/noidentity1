package com.jsfspring.curddemo.utills;

//
//@Configuration
//@EnableWebSecurity
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		// // require all requests to be authenticated except for the resources
//		http.authorizeRequests().antMatchers("/javax.faces.resource/**").permitAll().anyRequest().authenticated();
//		//
//		// // Login Pages
//		http.formLogin().loginPage("/login.jsf").permitAll().defaultSuccessUrl("/employeelist.jsf")
//				.failureForwardUrl("/login.jsf?error=true");
//		//
//		// // logout
//		http.logout().logoutSuccessUrl("/login.jsf");
//		//
//		// // not needed as JSF 2.2 is implicitly protected against CSRF
//		http.csrf().disable();
//
//		/*
//		 * http.csrf().disable().authorizeRequests().antMatchers("/", "/home",
//		 * "/about").permitAll()
//		 * .antMatchers("/admin/**").hasAnyRole("ADMIN").antMatchers("/user/**").
//		 * hasAnyRole("USER").anyRequest()
//		 * .authenticated().and().formLogin().loginPage("/login.jsf").permitAll().and().
//		 * logout().permitAll();
//		  
		
		http.authorizeRequests()
        .antMatchers("/secured/view/**").fullyAuthenticated()
        .antMatchers("/secured/admin/**", "/secured/view/admin/**").access("hasRole('ROLE_SUPERUSER')")
        .antMatchers("/index.xhtml", "/index.html", "/login.xhtml", "/javax.faces.resources/**").permitAll()
        .and()
        .formLogin()
        .and()
        .logout().logoutSuccessUrl("/login.xhtml").invalidateHttpSession(true).deleteCookies("JSESSIONID")
        .and()
        .exceptionHandling().accessDeniedPage("/error.xhtml")
        .and()
        .csrf().disable();
		
	}

	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("username").password("password").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("bharat").password("singh").roles("USER");
		auth.inMemoryAuthentication().withUser("test123").password("12345").roles("ADMIN");
	}

	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}*/

}
