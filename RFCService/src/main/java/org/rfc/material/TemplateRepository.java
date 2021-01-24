package org.rfc.material;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template,Integer>,TemplateRepositoryCustom {
	
	//public Template findById(int id);
	
}
