package org.rfc;

import org.rfc.material.MaterialRepository;
import org.rfc.material.messages.ReturnMessageRepository;
import org.rfc.material.run.RunRepository;
import org.rfc.material.runmaterial.RunMaterialRepository;
import org.rfc.material.template.TemplateRepository;
import org.rfc.material.template.TemplateValueRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = MaterialRepository.class)
@EnableJpaRepositories (basePackageClasses = {
				RunRepository.class,
				RunMaterialRepository.class,
				TemplateRepository.class,
				TemplateValueRepository.class,
				ReturnMessageRepository.class
				})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class RfcServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RfcServiceApplication.class, args);
	}

}
