package de.cqrity.vulnerapp;

import de.cqrity.vulnerapp.config.SecurityInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class Vulnerapp {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(12));
        factory.setMaxRequestSize(DataSize.ofMegabytes(12));
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(new Class[] { Vulnerapp.class, SecurityInitializer.class }, args);
    }
}
