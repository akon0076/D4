package com.cisdi.info.simple.dao.report;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.report.ModelFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "modelFileDao")
public interface ModelFileDao {

    public List<ModelFile> findModelFiles(PageDTO pageDTO);

    public List<ModelFile> findAllModelFiles();

    public List<ModelFile> findAllModelFilesWithIdName();

    public List<ModelFile> findModelFilesWithIdNameByName(@Param("modelFileName") String modelFileName);

    public ModelFile findModelFilesWithIdNameById(@Param(" modelFileId") Long modelFileId);

    public Long findModelFileTotalCount(PageDTO pageDTO);

    public ModelFile findModelFile(@Param("modelFileId") Long modelFileId);

    //所有外键的Name都以加载
    public ModelFile findModelFileWithForeignName(@Param("modelFileId") Long modelFileId);

    public Integer saveModelFile(ModelFile modelFile);

    public Integer updateModelFile(ModelFile modelFile);

    public Integer deleteModelFile(@Param("modelFileId") Long modelFileId);
}
