package com.jsfspring.curddemo;

import javax.faces.webapp.FacesServlet;
import javax.servlet.Servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import com.sun.faces.config.ConfigureListener;

@SpringBootApplication
public class CurddemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CurddemoApplication.class, args);
	}

	/*
	 * Below sets up the Faces Servlet for Spring Boot
	 */
	@Bean
	public FacesServlet facesServlet() {
		return new FacesServlet();
	}

	@Bean
	public ServletRegistrationBean<Servlet> facesServletRegistration() {
		ServletRegistrationBean<Servlet> registration = new ServletRegistrationBean<Servlet>(facesServlet(), "*.xhtml");
		registration.setName("FacesServlet");
		return registration;
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
	}

}
