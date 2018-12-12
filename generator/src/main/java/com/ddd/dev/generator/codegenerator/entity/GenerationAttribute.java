package com.ddd.dev.generator.codegenerator.entity;

import java.util.List;

import com.cisdi.info.simple.entity.base.EntityClass;

public class GenerationAttribute {

	private List<FieldAttribute> fieldAttributeList;
	
	private EntityClass entityClass ;

	public List<FieldAttribute> getFieldAttributeList() {
		return fieldAttributeList;
	}

	public void setFieldAttributeList(List<FieldAttribute> fieldAttributeList) {
		this.fieldAttributeList = fieldAttributeList;
	}

	public EntityClass getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(EntityClass entityClass) {
		this.entityClass = entityClass;
	}


}
