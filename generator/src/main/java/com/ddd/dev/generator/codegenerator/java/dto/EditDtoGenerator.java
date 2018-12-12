package com.ddd.dev.generator.codegenerator.java.dto;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.springframework.stereotype.Component;


@Component
public class EditDtoGenerator extends EntityClassGenerator {

	private String templateFile="frameResource/java/EditDto.vm";

	@Override
	public String getTemplateFile() {
		return this.templateFile;
	}
}
