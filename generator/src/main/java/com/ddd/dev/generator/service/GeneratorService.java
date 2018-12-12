package com.ddd.dev.generator.service;

import com.ddd.dev.generator.codegenerator.entity.GenerationAttribute;

import java.io.IOException;
import java.util.Map;

public interface GeneratorService {
    public Map<String, String> generateModel(String modelDefinition);

    public  Map<String,String> generateBaseCode(String className);

    public  String generateMethod(String controllerMethodName) throws IOException;
}

