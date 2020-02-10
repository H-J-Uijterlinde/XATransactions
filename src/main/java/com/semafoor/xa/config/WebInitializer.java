package com.semafoor.xa.config;

import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * The configure a web-application using only code (no xml) an implementation of the {@link WebApplicationInitializer}
 * must be provided. Classes implementing this interface will automatically be detected by springs
 * {@link SpringServletContainerInitializer}, this class is automatically bootstrapped in any Servlet 3.0 or higher
 * containers.
 *
 * Spring provides an implementation of the webApplicationInitializer in {@link AbstractAnnotationConfigDispatcherServletInitializer}
 * By extending this class and overriding its methods, the configuration can be customized
 */

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * This methods tells the web initializer where it can find the configuration for the application context. In this
     * case the configuration root class is {@link AppConfig}
     *
     * @return an array of classes defining the application context
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {AppConfig.class};
    }

    /**
     * This method tells the web initializer where it can find the web application context. In this case the web context
     * is not separated from the application context, which is already supplied by the getRootConfigClasses() method.
     * so we can return null here.
     *
     * @return an array of classes defining the web application context
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {

        // no application context hierarchy (only one context), so we can return null
        return null;
    }

    /**
     * The {@link DispatcherServlet} mappings are specified by this method.
     *
     * @return string array of mappings.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
