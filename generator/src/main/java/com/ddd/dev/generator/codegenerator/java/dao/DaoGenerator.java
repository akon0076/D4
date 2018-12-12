package com.ddd.dev.generator.codegenerator.java.dao;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import com.ddd.dev.generator.codegenerator.IGenerator;
import org.springframework.stereotype.Component;


@Component
public class DaoGenerator   extends EntityClassGenerator {

	private String templateFile="frameResource/java/Dao.vm";

	@Override
	public String getTemplateFile() {
		return this.templateFile;
	}
}
