package com.ddd.dev.generator.codegenerator.js;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.springframework.stereotype.Component;

@Component
public class JsServiceGenerator   extends EntityClassGenerator {
	private String templateFile= "frameResource/js/Service.vm";

	@Override
	public String getTemplateFile() {
		return this.templateFile;
	}
}
