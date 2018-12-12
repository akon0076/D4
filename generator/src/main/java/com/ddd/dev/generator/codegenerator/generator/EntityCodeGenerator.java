package com.ddd.dev.generator.codegenerator.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cisdi.info.simple.util.Config;
import com.cisdi.info.simple.entity.system.CodeTable;
import com.cisdi.info.simple.util.CodeTableManager;
import com.cisdi.info.simple.util.D4Util;
import com.ddd.dev.generator.codegenerator.entity.FieldAttribute;
import com.ddd.dev.generator.codegenerator.entity.GenerationAttribute;
import com.ddd.dev.generator.codegenerator.generator.impl.java.entity.EntityGenerator;
import com.ddd.dev.generator.codegenerator.util.GeneratorUtil;
import com.cisdi.info.simple.entity.base.EntityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityCodeGenerator {
	private static org.apache.logging.log4j.Logger logger = LogManager.getLogger();
//	@Resource(name="codeTypeServiceBean")
//	private static CodeTypeService codeTypeService = (CodeTypeService) SpringContextUtil.getBean("codeTypeServiceBean");

//	@Autowired
//	private GenerationParameter generationParameter;

	@Autowired
	private EntityGenerator entityGenerator;

	public static void main(String[] args) {
		String text = GeneratorUtil.readFile("C:/Users/Administrator/Desktop/javaE.txt");
		
	}
	
	public synchronized  Map<String,String> generatorEntity (String text){
		GenerationAttribute ga = new GenerationAttribute();
		ga.setEntityClass(new EntityClass());
		List<CodeTableConfig> CodeTableConfigs = new ArrayList<CodeTableConfig>();
		List<FieldAttribute> attris = new ArrayList<FieldAttribute>();
		Map<String, String> result = new HashMap<String, String>();
		result.put("successMsg", "");
		result.put("errorMsg", "");
		text = StringUtils.trimToEmpty(text);
		text = StringUtils.replace(text, " ", "");
		String[] rows = text.split("\n");
		for (int i = 0; i < rows.length; i++) {
			if((i+1)==rows.length){
				int tabNum=0;
				for (int k = 0; k < rows[i].length(); k++) {
					if(rows[i].charAt(k)=='\t'){
						tabNum++;
					}
				}
				if(tabNum<3){
					result.put("errorMsg", "第"+(i+1)+"行缺少数据，只有"+(tabNum)+"列");
					return result;
				}
				for (int j = 0; j < 6-tabNum; j++) {
					rows[i]=rows[i]+'\t';
				}
			}
			String[] cols = rows[i].split("[\t\r]");
			int j = 0;
//			for (; j < cols.length; j++) {
//				cols[j] = StringUtils.replace(cols[j]," ", "");
//				//System.out.print(cols[j]+"|");
//			}
			//System.out.println();
			if(cols.length<6){
				result.put("errorMsg", "第"+(i+1)+"行缺少数据，只有"+(j)+"列");
				return result;
			}
			
			if(i==0){
				if("".equals(cols[1])){
					result.put("errorMsg", "请写上实体名");
					return result;
				}
				if("".equals(cols[3])){
					result.put("errorMsg", "请写上中文名");
					return result;
				}
				String moduleName = cols[5];
				if("".equals(moduleName)){
					result.put("errorMsg", "请写上模块名");
					return result;
				}
                String[] moduleNames = StringUtils.split(moduleName, ":");
				if(moduleNames.length != 3)
				{
					result.put("errorMsg", "模块名名称格式为:  子系统代码:模块代码:模块名称，例如：pm:setting:项目管理");
					return result;
				}
				ga.getEntityClass().setClassName(StringUtils.capitalize(cols[1]) );
				ga.getEntityClass().setTableName(D4Util.convertCamelCaseName2UnderScore(ga.getEntityClass().getClassName()));
				ga.getEntityClass().setLabel(cols[3]);
				ga.getEntityClass().setSubSystemName(moduleNames[0]);
				ga.getEntityClass().setModuleName(moduleNames[1]);
				ga.getEntityClass().setModuleLabel(moduleNames[2]);
				ga.getEntityClass().setPackages(StringUtils.join(new String[]{Config.basePackage,ga.getEntityClass().getSubSystemName() ,"entity",ga.getEntityClass().getModuleName()}, "."));

			}else if(i>=2){
				if(cols[5].equals("")){
					result.put("errorMsg", "字段编号为"+(i-1)+"，字段名称为"+cols[1]+",的备注为空");
					return result;
				}
				FieldAttribute fa = new FieldAttribute();
				fa.setVisitModifier("private");
				fa.setAttributeModifier("");
				String type = cols[2].replaceAll(" *", "");
				type  = StringUtils.capitalize(type);
				if(type.charAt(0)>'Z'||type.charAt(0)<'A'){
					result.put("errorMsg", "属性："+cols[1]+"的类型："+type+"应该使用包装类型");
					return result;
				}
				if(type.contains("(")){
					int startIndex = type.indexOf("(");
					fa.setType(type.substring(0,startIndex));
					fa.getColumnInfo().setLength(Integer.parseInt(type.substring(startIndex+1,type.length()-1)));
				}else{
					fa.setType(type);
				}
				String fieldName = StringUtils.uncapitalize(cols[1].replaceAll(" *", ""));

				fa.setFieldName(fieldName);
				String columnName = D4Util.convertCamelCaseName2UnderScore(fieldName);
				fa.getColumnInfo().setColumnName(columnName);
				fa.getColumnInfo().setLabel(cols[3]);
				if(cols[4].toLowerCase().contains("非空")){
					fa.getColumnInfo().setNullable(false);
				}
				if(cols[4].toLowerCase().contains("唯一")){
					fa.getColumnInfo().setUnique(true);
				}
//				if(cols[5].toLowerCase().contains("true")){
//					//fa.getColumnInfo().setComposition(true);
//				}
				if( type.contains("CodeTable<")){
					int start = type.indexOf("<");
					int end = type.indexOf(">");
					String codeTypeKey = type.substring(start+1,end);
					List<String> codeTables = null;
					type = StringUtils.remove(type, ':');

					String temp = StringUtils.substringAfter(type, ">");
					temp = StringUtils.trimToEmpty(temp);
					type = StringUtils.remove(temp, ' ');
					codeTables = Arrays.asList(StringUtils.split(temp, ','));

					CodeTableConfig codeTableConfig =new CodeTableConfig();
					codeTableConfig.setCodeTypeKey(codeTypeKey);
					codeTableConfig.setCodeTypeName(cols[3]);
					codeTableConfig.setCodeTables(codeTables);
					CodeTableConfigs.add(codeTableConfig);
				}
				fa.getColumnInfo().setComment(cols[5]);
				attris.add(fa);
			}
		}
		ga.setFieldAttributeList(attris);
//		PrimaryGenerator pg = new PrimaryGenerator(ga);
		this.entityGenerator.initialize(ga);
		try {
			Class.forName(ga.getEntityClass().getClassFullName());
			//result.put("errorMsg", "已存在实体："+ga.getEntityClass().getClassFullName());
//			return result;
			logger.warn("已存在实体："+ga.getEntityClass().getClassFullName());
		} catch (ClassNotFoundException e) {
		}
		this.entityGenerator.generate();
		//实体生成成功之后去处理码表
		this.createCodeTables(CodeTableConfigs);
		result.put("successMsg", ga.getEntityClass().getClassFullName());
		return result;
	}
	private void createCodeTables(List<CodeTableConfig>  codeTableConfigs)
	{
		for(CodeTableConfig codeTableConfig : codeTableConfigs)
		{
			List<CodeTable> codeTables = new ArrayList<CodeTable>();
			for(String name : codeTableConfig.getCodeTables())
			{
				codeTables.add(new CodeTable(codeTableConfig.getCodeTypeKey(),name));
			}
			CodeTableManager.addCodeTables(codeTableConfig.getCodeTypeKey(),codeTables );
		}
	}
//	public synchronized  Map<String, String> generatorEntityCode (String text){
//		GenerationAttribute ga = new GenerationAttribute();
//		List<FieldAttribute> attris = new ArrayList<FieldAttribute>();
//		String[] rows = text.split("\n");
//		for (int i = 0; i < rows.length; i++) {
//			if((i+1)==rows.length){
//				int tabNum=0;
//				for (int k = 0; k < rows[i].length(); k++) {
//					if(rows[i].charAt(k)=='\t'){
//						tabNum++;
//					}
//				}
//				for (int j = 0; j < 6-tabNum; j++) {
//					rows[i]=rows[i]+'\t';
//				}
//			}
//			rows[i] = rows[i].replaceAll("\t", "\t ");
//			String[] cols = rows[i].split("[\t\r]");
//			int j = 0;
//			for (; j < cols.length; j++) {
//				cols[j] = cols[j].replaceFirst(" ", "");
//				//System.out.print(cols[j]+"|");
//			}
//			//System.out.println();
//			if(i==0){
//				ga.setClassNameUp(cols[1]);
//				ga.getEntityClass().setLabel(cols[3]);
//				ga.setFullPackageName(generationParameter.getBASE_PACKAGE()+".entity."+cols[5]);
//			}else if(i>=2){
//				FieldAttribute fa = new FieldAttribute();
//				fa.setVisitModifier("private");
//				fa.setAttributeModifier("");
//				String type = cols[2];
//				if(type.contains("(")){
//					int startIndex = type.indexOf("(");
//					fa.setType(type.substring(0,startIndex));
//					fa.getColumnInfo().setLength(Integer.parseInt(type.substring(startIndex+1,type.length()-1)));
//				}else{
//					fa.setType(type);
//				}
//				fa.setFieldName(cols[1]);
//				fa.getColumnInfo().setLabel(cols[3]);
//				if(cols[4].toLowerCase().contains("非空")){
//					fa.getColumnInfo().setNullable(false);
//				}
//				if(cols[4].toLowerCase().contains("唯一")){
//					fa.getColumnInfo().setUnique(true);
//				}
////				if(cols[5].toLowerCase().contains("true")){
////					fa.getColumnInfo().setComposition(true);
////				}
//				if(!cols[5].equals("")){
//					fa.getColumnInfo().setComment(cols[6]);
//				}
//				attris.add(fa);
//			}
//		}
//		ga.setFieldAttributeList(attris);
//		PrimaryGenerator pg = new PrimaryGenerator(ga);
//
//		//pg.generate();
//		EntityGenerator entityGenerator = new EntityGenerator();
//		entityGenerator.setCoverWrite(null);
//		entityGenerator.generate();
//
//		Map<String, String> result= new HashMap<String, String>();
//		result.put("fullClassName", generationParameter.getFullClassName());
//		result.put("javaCode", generationParameter.getJavaCode());
//		return result;
//	}
//
	

}
