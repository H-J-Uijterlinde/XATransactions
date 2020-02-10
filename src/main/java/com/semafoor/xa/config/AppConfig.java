package com.semafoor.xa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Base configuration class, used to group all other configuration classes. Beans that do not logically  belong to any
 * more specific configuration class can be defined here.
 */

@Configuration
@ComponentScan(basePackages = {"com.semafoor.xa.config"})
public class AppConfig {
}
