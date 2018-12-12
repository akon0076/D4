package com.ddd.dev.generator.codegenerator.java.controller;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.springframework.stereotype.Component;

@Component
public class ControllerGenerator extends EntityClassGenerator {
	private String templateFile="frameResource/java/Controller.vm";

	@Override
	public String getTemplateFile() {
		return this.templateFile;
	}
}
