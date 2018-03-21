package com.jsfspring.curddemo.utills;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig/* extends WebSecurityConfigurerAdapter */ {

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception { //
	 * require all requests to be authenticated except for the resources
	 * http.authorizeRequests().antMatchers("/javax.faces.resource/**").permitAll().
	 * anyRequest().authenticated();
	 * 
	 * // Login Pages
	 * http.formLogin().loginPage("/login.jsf").permitAll().failureForwardUrl(
	 * "/login.xhtml?error=true");
	 * 
	 * // logout http.logout().logoutSuccessUrl("/login.xhtml");
	 * 
	 * // not needed as JSF 2.2 is implicitly protected against CSRF
	 * http.csrf().disable(); }
	 * 
	 * @Bean
	 * 
	 * @Override protected UserDetailsService userDetailsService() { UserDetails
	 * user = User.withUsername("username").password("password").roles("USER")
	 * .build(); return new InMemoryUserDetailsManager(user); }
	 * 
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception {
	 * auth.inMemoryAuthentication().withUser("mkyong").password("123456").roles(
	 * "USER");
	 * auth.inMemoryAuthentication().withUser("admin").password("123456").roles(
	 * "ADMIN");
	 * auth.inMemoryAuthentication().withUser("dba").password("123456").roles("DBA")
	 * ; }
	 */
}
