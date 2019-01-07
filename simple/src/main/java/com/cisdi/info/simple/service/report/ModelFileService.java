package com.cisdi.info.simple.service.report;

import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.report.ModelFile;
import com.cisdi.info.simple.dto.base.PageDTO;

import java.util.List;

public interface ModelFileService {

    public PageResultDTO findModelFiles(PageDTO pageDTO);

    public List<ModelFile> findAllModelFiles();

    public List<ModelFile> findModelFilesWithIdNameByName(String modelFileName);

    public List<ModelFile> findAllModelFilesWithIdName();

    public ModelFile findModelFilesWithIdNameById(Long modelFileId);

    public ModelFile findModelFile(Long modelFileId);

    //所有外键的Name都以加载
    public ModelFile findModelFileWithForeignName(Long modelFileId);

    public ModelFile saveModelFile(ModelFile modelFile);

    public ModelFile updateModelFile(ModelFile modelFile);

    public void deleteModelFile(Long modelFileId);
}
