package com.kasia.config;

import com.kasia.config.repository.RepositoryConfig;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Import(RepositoryConfig.class)
@ComponentScan("com.kasia")
public class AppConfig {

    private final String PROPERTIES_FILE_NAME = "app.properties";

    //CONFIG connection to property file ------------------------------------------------------------
    @Bean
    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource(PROPERTIES_FILE_NAME));
        return ppc;
    }
}
