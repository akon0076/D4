package com.ddd.dev.generator.codegenerator.generator.chain;

import com.ddd.dev.generator.codegenerator.IGenerator;

public interface GeneratorChain {
	
	public void execute();
	
	public void addGenerator(IGenerator generator);

}
