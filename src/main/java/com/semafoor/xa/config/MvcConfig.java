package com.semafoor.xa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to enable springs web mvc modules. @EnableWebMvc imports springs web mvc configuration from
 * {@link WebMvcConfigurationSupport}. Implementing WebMvcConfigurer allows customization of the default configuration
 * by overriding its methods.
 */

@Configuration
@ComponentScan(basePackages = {"com.semafoor.xa.web"})
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
}
