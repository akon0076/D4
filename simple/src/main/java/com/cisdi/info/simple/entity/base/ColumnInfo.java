package com.cisdi.info.simple.entity.base;

import com.cisdi.info.simple.annotation.DColumn;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *@author 作者:吴桥伟
 *@version 创建时间：2015年1月28日 下午3:45:27
 * 类说明
 */
public class  ColumnInfo {

	private int index = 0;

	private String label="";

	private String fieldName="";

	private String columnName="";

	private String foreignEntity = "";

	private boolean Id = false;
	
	public String codeTable="";

	public String constrains = "";

	private String columnDefinition="";

	private String definitionType="";

	private int length;

	private boolean nullable = true;

	private int precision;

	private int scale;

	private boolean unique = false;
	
	private String uniqueName="";

	private String comment="";

	private Field field;
	
	private Method fieldGetter;
	
	private Method fieldSetter;
	
	private Class<?> fieldType;

	private DColumn dColumn;

	private Column column;


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getForeignEntity() {
		return foreignEntity;
	}

	public void setForeignEntity(String foreignEntity) {
		this.foreignEntity = foreignEntity;
	}

	public boolean isId() {
		return Id;
	}

	public void setId(boolean id) {
		Id = id;
	}

	public String getCodeTable() {
		return codeTable;
	}

	public void setCodeTable(String codeTable) {
		this.codeTable = codeTable;
	}

	public String getConstrains() {
		return constrains;
	}

	public void setConstrains(String constrains) {
		this.constrains = constrains;
	}

	public String getColumnDefinition() {
		return columnDefinition;
	}

	public void setColumnDefinition(String columnDefinition) {
		this.columnDefinition = columnDefinition;
	}

	public int getLength() {
		return length<=0?255:length;
	}

	public void setLength(int length) {
		if(length <=0)
		{
			length=255;
		}
		this.length = length;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Method getFieldGetter() {
		return fieldGetter;
	}

	public void setFieldGetter(Method fieldGetter) {
		this.fieldGetter = fieldGetter;
	}

	public Method getFieldSetter() {
		return fieldSetter;
	}

	public void setFieldSetter(Method fieldSetter) {
		this.fieldSetter = fieldSetter;
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	public String getDefinitionType() {
		return definitionType;
	}

	public void setDefinitionType(String definitionType) {
		this.definitionType = definitionType;
	}

	public DColumn getdColumn() {
		return dColumn;
	}

	public void setdColumn(DColumn dColumn) {
		this.dColumn = dColumn;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}
}
