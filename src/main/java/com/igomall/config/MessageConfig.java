package com.igomall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@Configuration
public class MessageConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setCacheSeconds(0);
        reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        reloadableResourceBundleMessageSource.setBasenames("WEB-INF/language/common/message","WEB-INF/language/shop/message","WEB-INF/language/member/message","WEB-INF/language/business/message","WEB-INF/language/admin/message");
        return reloadableResourceBundleMessageSource;
    }

    @Bean
    public FixedLocaleResolver localeResolver(){
        FixedLocaleResolver fixedLocaleResolver = new FixedLocaleResolver();
        return fixedLocaleResolver;
    }
}
