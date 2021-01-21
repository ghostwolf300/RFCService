package org.rfc.material;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.rfc.material.dto.FieldValueDTO;
import org.rfc.material.dto.MaterialTemplateDTO;

@Entity
@Table(name="t_material_template")
public class MaterialTemplate {
	@Id
	@Column(name="id")
	private int id;
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy="template")
	private Set<MaterialTemplateValue> fieldValues;
	
	public MaterialTemplate() {
		super();
	}
	
	public MaterialTemplate(MaterialTemplateDTO dto) {
		this.id=dto.getId();
		this.name=dto.getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
