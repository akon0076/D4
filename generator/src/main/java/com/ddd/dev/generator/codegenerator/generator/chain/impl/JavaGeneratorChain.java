package com.ddd.dev.generator.codegenerator.generator.chain.impl;

import java.util.ArrayList;
import java.util.List;

import com.ddd.dev.generator.codegenerator.IGenerator;
import com.ddd.dev.generator.codegenerator.generator.chain.GeneratorChain;

public class JavaGeneratorChain implements GeneratorChain {
	
	private List<IGenerator> chain = new ArrayList<IGenerator>();

	@Override
	public void execute() {
		for(IGenerator generator : chain) {
			generator.generate();
		}
	}


	@Override
	public void addGenerator(IGenerator javagenerator) {
		chain.add(javagenerator);
	}

}
