package com.mooracle.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/** ENTRY LIST
 *  ENTRY 12: Integrating User Data  Into Our Application.
 * */

@Configuration
public class TemplateConfig {
//begin with template resolver

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    /** next the template engine
     *   Entry 12-1: adding the thymeleaf extras for spring security
     *      insert the Spring Security dialect
     *   Note: the main reason we use Java based config for the template instead of XML based config is to add this
     *      functions such as dialects. This dialect then can be accessed in every html templates in the resources
     *      directory. One we interested on is the layout template which will render the username in the nav bar
     *
     * */
    @Bean
    public SpringTemplateEngine templateEngine() {
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(templateResolver());
        springTemplateEngine.addDialect(new SpringSecurityDialect());//this is the added security dialect
        return springTemplateEngine;
    }

    //last the Thymeleaf viewer
    @Bean
    public ThymeleafViewResolver viewResolver() {
        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }
}
