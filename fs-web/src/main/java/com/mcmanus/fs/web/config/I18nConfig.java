package com.mcmanus.fs.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class I18nConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        final ReloadableResourceBundleMessageSource result = new ReloadableResourceBundleMessageSource();
        result.setDefaultEncoding("UTF-8");
        result.setBasename("classpath:locale/errors");
        return result;
    }
}
