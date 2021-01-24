package org.rfc.material;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.NamedNativeQuery;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;

import org.rfc.material.dto.HeaderColumnDTO;
import org.rfc.material.dto.TemplateDTO;

@Entity
@Table(name="t_material_template")
@NamedNativeQuery(
		name="UploadHeaders",
		query="select concat(bapi_structure,'-',bapi_field) header_text,field_index from t_material_template_values where template_id=:templateId and input_type=1 group by bapi_structure,bapi_field,field_index order by field_index",
		resultSetMapping="UploadHeadersResult"
)
@SqlResultSetMapping(
		name="UploadHeadersResult",
		classes=@ConstructorResult(
				targetClass=HeaderColumnDTO.class,
				columns= {
						@ColumnResult(name="header_text"),
						@ColumnResult(name="field_index")
				}
		)
		
		
)
public class Template {
	@Id
	@Column(name="id")
	private int id;
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy="template")
	private Set<TemplateValue> fieldValues;
	
	public Template() {
		super();
	}
	
	public Template(TemplateDTO dto) {
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

	public Set<TemplateValue> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(Set<TemplateValue> fieldValues) {
		this.fieldValues = fieldValues;
	}
	
}
