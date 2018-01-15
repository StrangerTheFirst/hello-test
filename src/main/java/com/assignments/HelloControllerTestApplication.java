package com.assignments;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@Configuration
public class HelloControllerTestApplication {
	@Autowired
	private DispatcherServlet servlet;

	@Bean
	public CommandLineRunner getCommandLineRunner(ApplicationContext context) {
		servlet.setThrowExceptionIfNoHandlerFound(true);
		return args -> {};
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloControllerTestApplication.class, args);
	}
}
