package org.ud2.developers.SGPVADU.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:tmp//"); // Windows
		// registry.addResourceHandler("/images/**").addResourceLocations("file:src//main//resources//static//images/");
	}
}