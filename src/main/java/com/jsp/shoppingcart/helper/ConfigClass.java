package com.jsp.shoppingcart.helper;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ComponentScan(basePackages = "com")
@Configuration //it used to specify container to specify the source of bean defination
public class ConfigClass {
	
	@Bean
	public InternalResourceViewResolver resolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setSuffix(".jsp");
		resolver.setPrefix("/");
		
		return resolver;
	}
	
	@Bean
	public EntityManagerFactory getEMF() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dev");
		return emf;
	}
}
