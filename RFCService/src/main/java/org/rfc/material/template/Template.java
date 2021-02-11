package org.rfc.material.template;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.NamedNativeQuery;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="name")
	private String name;
	
	//added @OnDelete and orphanRemoval
	//@OneToMany(mappedBy="template",orphanRemoval=true,fetch=FetchType.LAZY)
	//@OnDelete(action = OnDeleteAction.CASCADE)
	//Update and insert works... Delete doesn't delete template values
	@OneToMany(mappedBy="template",fetch=FetchType.LAZY)
	private Set<TemplateValue> fieldValues;
	
	public Template() {
		super();
	}
	
	public Template(TemplateDTO dto) {
		if(dto.getId()==-1) {
			this.id=null;
		}
		else {
			this.id=dto.getId();
		}
		this.name=dto.getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Template other = (Template) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
