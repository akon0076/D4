package com.ddd.dev.generator.codegenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.service.permission.OperatorService;
import com.cisdi.info.simple.util.Config;
import com.cisdi.info.simple.util.ModuleManager;
import com.cisdi.info.simple.util.SpringContextUtils;
import com.ddd.dev.generator.codegenerator.java.controller.ControllerGenerator;
import com.ddd.dev.generator.codegenerator.java.dao.DaoGenerator;
import com.ddd.dev.generator.codegenerator.java.dao.DaoImplGenerator;
import com.ddd.dev.generator.codegenerator.java.dto.EditDtoGenerator;
import com.ddd.dev.generator.codegenerator.java.service.ServiceGenerator;
import com.ddd.dev.generator.codegenerator.java.service.ServiceImplGenerator;
import com.ddd.dev.generator.codegenerator.js.*;
import com.ddd.dev.generator.codegenerator.util.RouteUtil;
import com.cisdi.info.simple.entity.base.EntityClass;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseCodeGenerator {
	private static Logger logger = LogManager.getLogger();
//	public static void main(String[] args) {
//		//String className="ddd.simple.entity.school.Course";
//		//new Generator().generatorBaseCode(className);
//		Map map = new HashMap();
//		map.put("date", new Date().toLocaleString());
//		String result = generate("D://angular/workspace/DDD3/src/ddd/test1.vm", map);
//		System.out.println(result);
//	}
	@Autowired
	private OperatorService operatorService;
	public BaseCodeGenerator() {
		
	}
	
	public Map<String,String> generateBaseCode(String className){
		Map<String, String> result = new HashMap<String, String>();
		result.put("successMsg", "");
		result.put("errorMsg", "");
		
		if(className==null||"".equals(className)){
			result.put("errorMsg", "请输入完整类名");
			return result;
		}
		List<IGenerator> generators = new ArrayList<IGenerator>();

		EntityClass entityClass = null;
		try {
			entityClass = new EntityClass(className);
		} catch (ClassNotFoundException e) {
			String error =  String.format("没有找到类%s,如果是刚才生成实体，需要重启服务器",className) ;
			logger.error(error);
			throw  new DDDException(error);
		}

		//java代码生成
		generators.add(SpringContextUtils.getBean(DaoGenerator.class).initialize(entityClass));
		generators.add(SpringContextUtils.getBean(DaoImplGenerator.class).initialize(entityClass));
		generators.add(SpringContextUtils.getBean(ServiceGenerator.class).initialize(entityClass));
		generators.add(SpringContextUtils.getBean(ServiceImplGenerator.class).initialize(entityClass));
		AbstractGenerator controllerGenerator = SpringContextUtils.getBean(ControllerGenerator.class).initialize(entityClass);
		generators.add(controllerGenerator);
		generators.add(SpringContextUtils.getBean(EditDtoGenerator.class).initialize(entityClass));

//		//js代码生成
		generators.add(SpringContextUtils.getBean(JsServiceGenerator.class).initialize(entityClass));
		generators.add(SpringContextUtils.getBean(JsFormGenerator.class).initialize(entityClass));
		generators.add(SpringContextUtils.getBean(JsListGenerator.class).initialize(entityClass));
		generators.add(SpringContextUtils.getBean(JsDisplayGenerator.class).initialize(entityClass));
		generators.add(SpringContextUtils.getBean(JsRouteGenerator.class).initialize(entityClass));

		for (IGenerator generator : generators) {
			generator.generate();
		}

		RouteUtil.mergeRoutes(Config.uiPath+ File.separator+"src/view");
		try {
			this.createModule(entityClass,controllerGenerator.getLastOutputFile());
		} catch (IOException e) {
			e.printStackTrace();
			String error =  String.format("根据Controller类生成模块出错：%s",controllerGenerator.getLastOutputFile()) ;
			logger.error(error);
			throw  new DDDException(error);
		}
		//创建超级用户，生成权限
		this.operatorService.createSuperUser();

		result.put("successMsg", "生成代码成功");
		return result;
	}
	private void createModule(EntityClass entityClass,String controllerFile) throws IOException {
		String code = FileUtils.readFileToString( new File(controllerFile), "UTF-8");
		String moduleJson = StringUtils.substringBetween(code, "/**module", "*/");
		if(moduleJson == null || StringUtils.trimToEmpty(moduleJson).equals(""))
		{
			throw  new DDDException(String.format("在文件 %s 没找到 module 定义的JSON", controllerFile));
		}
		ModuleManager.addModuleFromJsonCodeGenerate(moduleJson);
	}

//	private void createModule(EntityClass entityClass)
//	{
//		String code = StringUtils.join(new String[]{entityClass.getSubSystemName(),entityClass.getModuleName(),entityClass.getClassName() }, '/');
//		String url ="/"+StringUtils.join(new String[]{entityClass.getSubSystemName(),entityClass.getModuleName(),entityClass.getClassName() }, '/');
//		String route ="/"+StringUtils.join(new String[]{entityClass.getSubSystemName(),entityClass.getModuleName(),entityClass.getClassName() }, '/');
//		ModuleManager.addModule(code, entityClass.getLabel(),url,route, "", 1l,"电脑模块" ,"",entityClass.getModuleLabel() );
//	}
//
//	public  String generate(String absoluteTemplatePath,Map replaceMap) throws Exception {
//		VelocityEngine velocityEngine = new VelocityEngine();
//		Properties p = new Properties();
//		p.put(VelocityEngine.FILE_RESOURCE_LOADER_PATH,"");
//		velocityEngine.init(p);
//		File file = new File(absoluteTemplatePath);
//		if(!file.exists()){
//			System.err.println("模板不存在："+file.getAbsolutePath());
//			return null;
//		}
//		Template template = velocityEngine.getTemplate(absoluteTemplatePath, config.CHARSET_UTF8);
//		VelocityContext velocityContext = new VelocityContext(replaceMap);
//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
//		template.merge(velocityContext, outputStreamWriter);
//		try {
//			outputStreamWriter.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return new String(byteArrayOutputStream.toByteArray());
//	}
//
//	public static void generate(VelocityContext velocityContext,String templateFile){
//		velocityContext.put("htmlAndjsCodeGenPath", Config.htmlAndjsCodeGenPath);
//		velocityContext.put("applicationName", Config.applicationName);
//		velocityContext.put("sbuSystem", Config.sbuSystem);
//		try {
//			StringWriter stringWriter = new StringWriter();
//			VelocityEngine velocityEngine = new VelocityEngine();
//			Properties p = new Properties();
//			p.put(VelocityEngine.FILE_RESOURCE_LOADER_PATH, SessionFactory.getConfig().get("applicationPath"));
//			velocityEngine.init(p);
//			velocityEngine.mergeTemplate(templateFile, Config.CHARSET_UTF8, velocityContext, stringWriter);
//			String outputFile = velocityContext.get("outputFile").toString();
//			outputFile=SessionFactory.getConfig().get("applicationPath") + outputFile ;
//			File file = new File(outputFile);
//			if(file.exists()){
//				System.err.println("文件已存在："+file.getAbsolutePath());
//				return;
//			}
//			File pDirec = new File(file.getParent());
//			if(!pDirec.exists()) pDirec.mkdirs();
//			Template template = velocityEngine.getTemplate(templateFile, Config.CHARSET_UTF8);
//			FileOutputStream fileOutputStream = new FileOutputStream(file);
//			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,Config.CHARSET_UTF8);
//			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
//			template.merge(velocityContext,bufferedWriter);
//			bufferedWriter.flush();
//			bufferedWriter.close();
//			System.out.println("生成文件："+file.getAbsolutePath());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	


}
