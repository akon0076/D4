package com.ddd.dev.generator.codegenerator.java.dao;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Component;

@Component
public class DaoPyGenerator   extends EntityClassGenerator {

	private String templateFile="frameResource/py/Dao.py";

	@Override
	public String getTemplateFile() {
		return this.templateFile;
	}
}
