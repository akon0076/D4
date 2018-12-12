package com.cisdi.info.simple.entity.base;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.util.ClassUtil;

import org.apache.commons.lang3.StringUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.*;

import static java.lang.Class.forName;


/**
 * 实体信息类
 */
public class EntityClass{


	/**
	 * 类的名称
	 */
	private String className;


	/**
	 * 数据库名称
	 */
	private String tableName;


	/**
	 * 中文名称
	 */
	private String label="";

	/**
	 * 类的包名
	 */
	private String packages;
	/**
	 * 当前EntityClass对应的Class
	 */
	private Class  clazz;

	/**
	 * 子系统名称
	 */
	private String subSystemName ;
	/**
	 * 模块名称
	 */
	private String moduleName;

	/**
	 * 模块标题
	 */
	private String moduleLabel;
	/**
	 * 说明
	 */
	private String comment="";


	private LinkedHashMap<String,Field> classFields = new LinkedHashMap<String, Field>();

	/**
	 * String是数据库字段名,Field属性上的Field
	 */
	private LinkedHashMap<String,Field> dbFields = new LinkedHashMap<String, Field>();

	/**
	 * String是类的属性名，即Field.name,ColumnInfo是Field属性上的注解
	 */
	private LinkedHashMap<String,ColumnInfo> columnInfosByFieldName = new LinkedHashMap<String, ColumnInfo>();
	
	/**
	 * String是数据库字段名,ColumnInfo是Field属性上的注解
	 */
	private LinkedHashMap<String,ColumnInfo> columnInfosByColumnName = new LinkedHashMap<String, ColumnInfo>();

	private List<ColumnInfo> columnInfos =  new ArrayList<ColumnInfo>();

	/**
	 * 保存到数据库中的 属性
	 */
	private List<ColumnInfo> dbColumnInfos =  new ArrayList<ColumnInfo>();

	/**
	 * 不保存到数据库，只用于显示
	 */
	private List<ColumnInfo> notDbColumnInfos =  new ArrayList<ColumnInfo>();

	/**
	 * 主键的Field
	 */
	private Field idField;
	
	/**
	 * 主键的Field的注解
	 */
	private ColumnInfo idColumnInfo;


	/**
	 * 所有字段的名称
	 */
	private String[] properties;


	public String getClassFullName()
	{
		return this.packages+"."+this.className;
	}
	public EntityClass(String className) throws ClassNotFoundException {

		Class clazz = forName(className);
		this.initialize(clazz);

	}
	public EntityClass() {
	}

	public EntityClass(Class clazz) {
		this.initialize(clazz);
	}
	public void initialize(Class clazz) {

		this.clazz = clazz;
		this.toEntityInfo(clazz);

		List<Field> fields = ClassUtil.getClassNestedFields(clazz);

		for(Field field : fields)
		{
			ColumnInfo columnInfo = toColumnInfo(field);
			if(columnInfo == null) continue;
			this.columnInfos.add(columnInfo);
		}
		Collections.sort(this.columnInfos, new Comparator<ColumnInfo>() {
			@Override
			public int compare(ColumnInfo o1, ColumnInfo o2) {
				int i = o1.getIndex() - o2.getIndex();
				return i;
			}
		});

		for(ColumnInfo columnInfo: this.columnInfos)
		{
			this.columnInfosByFieldName.put(columnInfo.getFieldName(), columnInfo);

			if(columnInfo.getColumn() != null)
			{
				this.columnInfosByColumnName.put(columnInfo.getColumnName(), columnInfo);
				this.dbColumnInfos.add(columnInfo);
			}
			else
			{
				this.notDbColumnInfos.add(columnInfo);
			}
		}
	}

	private ColumnInfo toColumnInfo(Field field){

		ColumnInfo columnInfo = new ColumnInfo();

		DColumn dColumn = field.getAnnotation(DColumn.class);
		if(dColumn == null) return null;

		columnInfo.setdColumn(dColumn);
		columnInfo.setFieldName(field.getName());

		columnInfo.setForeignEntity(dColumn.foreignEntity());
		columnInfo.setLabel(dColumn.label());
		columnInfo.setIndex(dColumn.index());
		columnInfo.setComment(dColumn.comment());
		columnInfo.setConstrains(dColumn.constrains());
		columnInfo.setCodeTable(dColumn.codeTable());

		//columnInfo.setId(column.Id());

		Column column = field.getAnnotation(Column.class);
		columnInfo.setColumn(column);
		if(column != null)
		{
			columnInfo.setColumnName("".equals(column.name().trim()) ? field.getName() : column.name());

			columnInfo.setColumnDefinition(column.columnDefinition());

			columnInfo.setLength(column.length());

			columnInfo.setNullable(column.nullable());

			columnInfo.setPrecision(column.precision());

			columnInfo.setScale(column.scale());
		}

		columnInfo.setField(field);


		if(field.getType().isPrimitive()){
			throw new RuntimeException("请使用包装类型："+field);
		}
		else if(field.getType() == String.class ||
			Boolean.class == field.getType() ||
			Character.class == field.getType() ||
			Byte.class == field.getType() ||
			Short.class == field.getType() ||
			Integer.class == field.getType() ||
			Long.class == field.getType() ||
			Float.class == field.getType() ||
			Double.class == field.getType() ||
			Date.class == field.getType() )
			{
				columnInfo.setFieldType(field.getType());
		}

		if(!"".equals(columnInfo.getCodeTable()))
		{
			columnInfo.setDefinitionType("CodeTable");
		}
		else if(! "".equals(columnInfo.getForeignEntity()))
		{
			if(columnInfo.getColumn() ==  null) {
				columnInfo.setDefinitionType("ForeignName");
			}
			else {
				columnInfo.setDefinitionType("ForeignId");
			}
		}
		else
		{
			columnInfo.setDefinitionType(field.getType().getSimpleName());
		}

//		String fieldNameFirstUp = field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
//		try {
//			columnInfo.setFieldGetter(field.getClass().getMethod("get"+fieldNameFirstUp));
//			columnInfo.setFieldSetter(field.getClass().getMethod("set"+fieldNameFirstUp));
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		}
		return columnInfo;
	}
	
	private void toEntityInfo(Class clazz){
		this.clazz = clazz;
		this.className= clazz.getSimpleName();
		this.packages = clazz.getPackage().getName();

		DEntity dEntity = (DEntity)clazz.getDeclaredAnnotation(DEntity.class);

		this.label = dEntity.label();
		this.comment = dEntity.comment();
		String[] temps = StringUtils.split(this.packages,".");
		this.subSystemName = temps[temps.length-3];
		this.moduleName = temps[temps.length-1];


		this.moduleLabel = dEntity.moduleLabel();

		Entity entity = (Entity)clazz.getDeclaredAnnotation(Entity.class);

		this.tableName = entity.name();

		this.className =this.clazz.getSimpleName();

	}

	/**
	 * 根据注解上的name获取注解信息
	 * @param fieldName
	 * @return
	 */
	public ColumnInfo getColumnInfoByFieldName(String fieldName){
		return this.columnInfosByFieldName.get(fieldName);
	}
	/**
	 * 根据属性名获取注解信息
	 * @param columnName
	 * @return
	 */
	public ColumnInfo getColumnInfoByColumnName(String columnName){
		return this.columnInfosByColumnName.get(columnName);
	}


	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleLabel() {
		return moduleLabel;
	}

	public void setModuleLabel(String moduleLabel) {
		this.moduleLabel = moduleLabel;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LinkedHashMap<String, Field> getClassFields() {
		return classFields;
	}

	public void setClassFields(LinkedHashMap<String, Field> classFields) {
		this.classFields = classFields;
	}

	public LinkedHashMap<String, Field> getDbFields() {
		return dbFields;
	}

	public void setDbFields(LinkedHashMap<String, Field> dbFields) {
		this.dbFields = dbFields;
	}

	public LinkedHashMap<String, ColumnInfo> getColumnInfosByFieldName() {
		return columnInfosByFieldName;
	}

	public void setColumnInfosByFieldName(LinkedHashMap<String, ColumnInfo> columnInfosByFieldName) {
		this.columnInfosByFieldName = columnInfosByFieldName;
	}

	public LinkedHashMap<String, ColumnInfo> getColumnInfosByColumnName() {
		return columnInfosByColumnName;
	}

	public void setColumnInfosByColumnName(LinkedHashMap<String, ColumnInfo> columnInfosByColumnName) {
		this.columnInfosByColumnName = columnInfosByColumnName;
	}

	public List<ColumnInfo> getColumnInfos() {
		return columnInfos;
	}

	public void setColumnInfos(List<ColumnInfo> columnInfos) {
		this.columnInfos = columnInfos;
	}

	public List<ColumnInfo> getDbColumnInfos() {
		return dbColumnInfos;
	}

	public void setDbColumnInfos(List<ColumnInfo> dbColumnInfos) {
		this.dbColumnInfos = dbColumnInfos;
	}

	public List<ColumnInfo> getNotDbColumnInfos() {
		return notDbColumnInfos;
	}

	public void setNotDbColumnInfos(List<ColumnInfo> notDbColumnInfos) {
		this.notDbColumnInfos = notDbColumnInfos;
	}

	public Field getIdField() {
		return idField;
	}

	public void setIdField(Field idField) {
		this.idField = idField;
	}

	public ColumnInfo getIdColumnInfo() {
		return idColumnInfo;
	}

	public void setIdColumnInfo(ColumnInfo idColumnInfo) {
		this.idColumnInfo = idColumnInfo;
	}

	public String[] getProperties() {
		return properties;
	}

	public void setProperties(String[] properties) {
		this.properties = properties;
	}

	public String getSubSystemName() {
		return subSystemName;
	}

	public void setSubSystemName(String subSystemName) {
		this.subSystemName = subSystemName;
	}
}
