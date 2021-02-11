package org.rfc.material.template;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TemplateValueRepository extends JpaRepository<TemplateValue,TemplateValueKey> {
	
	public List<TemplateValue> findByKeyTemplateId(int templateId);
	public List<TemplateValue> findByKeyTemplateIdAndKeyBapiStructureAndKeyRowId(int templateId,String structure,int rowId);
	
	@Query("select tv.key.rowId from TemplateValue tv where tv.key.templateId=:templateId and tv.key.bapiStructure=:tableName group by tv.key.rowId order by tv.key.rowId")
	public Integer[] getTableRowIndexes(int templateId,String tableName);
	
}
