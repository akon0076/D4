package com.ddd.dev.generator.codegenerator.js;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.springframework.stereotype.Component;

@Component
public class JsFormGenerator extends EntityClassGenerator {
	private String templateFile= "frameResource/js/Form.vm";

	@Override
	public String getTemplateFile() {
		return this.templateFile;
	}
}
