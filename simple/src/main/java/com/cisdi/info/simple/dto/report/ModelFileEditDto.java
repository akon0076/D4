package com.cisdi.info.simple.dto.report;


import com.cisdi.info.simple.entity.report.ModelFile;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
public class ModelFileEditDto{

    private ModelFile modelFile;




    public  ModelFile getModelFile()
    {
        return this.modelFile;
    }
    public  void setModelFile(ModelFile modelFile)
    {
        this.modelFile = modelFile;
    }

}
