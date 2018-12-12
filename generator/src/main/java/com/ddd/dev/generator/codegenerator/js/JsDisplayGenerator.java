package com.ddd.dev.generator.codegenerator.js;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.springframework.stereotype.Component;

@Component
public class JsDisplayGenerator   extends EntityClassGenerator {
	private String templateFile= "frameResource/js/Display.vm";


	@Override
	public String getTemplateFile() {
		return this.templateFile;
	}
}
