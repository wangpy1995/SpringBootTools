package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class MultiSuffixTemplateConfig {

    @Value("${spring.thymeleaf.prefix}")
    private String prefix;
    @Value("${spring.thymeleaf.suffix}")
    private String suffix;
    @Value("${spring.thymeleaf.mode}")
    private String mode;
    @Value("${spring.thymeleaf.encoding}")
    private String encoding;
    @Value("${spring.thymeleaf.content-type}")
    private String contentType;

    @Bean
    public SpringResourceTemplateResolver templateResolver() {

        SpringResourceTemplateResolver templateResolver = new MultiSuffixResourceTemplateResolver();
        templateResolver.setPrefix(prefix);
        // Multiple suffixes separated by commas
        templateResolver.setSuffix(suffix);
        templateResolver.setTemplateMode(mode);
        templateResolver.setCharacterEncoding(encoding);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

}
