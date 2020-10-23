package org.rfc.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebMvc
public class ThymeleafConfig implements WebMvcConfigurer, ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;

	}
	
	@Bean
	public ViewResolver htmlViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
		resolver.setContentType("text/html");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(new String[] {"*.html"});
		return resolver;
	}
	
	@Bean
    public ViewResolver javascriptViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine(javascriptTemplateResolver()));
        resolver.setContentType("application/javascript");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setViewNames(new String[] {"*.js"});
        return resolver;
    }
    
    private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(new LayoutDialect());
        engine.setTemplateResolver(templateResolver);
        return engine;
    }
    

    private ITemplateResolver htmlTemplateResolver() {
    	SpringResourceTemplateResolver resolver = new
    			SpringResourceTemplateResolver();
    	resolver.setApplicationContext(applicationContext);
    	resolver.setPrefix("templates/"); resolver.setCacheable(false);
    	resolver.setSuffix(".html"); resolver.setTemplateMode(TemplateMode.HTML);
    	return resolver; 
    }

    private ITemplateResolver javascriptTemplateResolver() {
    	SpringResourceTemplateResolver resolver = new
    			SpringResourceTemplateResolver();
    	resolver.setApplicationContext(applicationContext);
    	resolver.setPrefix("static/js/"); resolver.setCacheable(false);
    	resolver.setSuffix(".js"); resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
    	return resolver; 
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          	.addResourceHandler("/js/**","/css/**","/img/**","/webjars/**")
          	.addResourceLocations("classpath:/static/js/","classpath:/static/css/","classpath:/static/img/","/webjars/");
    }
	
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/").setViewName("home");
		//registry.addViewController("/token").setViewName("token");
	}
	 
         

}
