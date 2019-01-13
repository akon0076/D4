package com.cisdi.info.simple.entity.report;

import com.cisdi.info.simple.entity.base.*;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;



@DEntity(label="模板文件管理",comment="",moduleLabel="报表管理")
@Entity(name="simple_model_file")
public class ModelFile extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="模板唯一标识",comment="模板唯一标识")
	@Column(name="model_key",length=255,nullable=false,unique=false)
	private String modelKey;

	@DColumn(index=4,label="模板名称",comment="模板名称")
	@Column(name="model_name",length=255,nullable=false,unique=false)
	private String modelName;

	@DColumn(index=5,label="分类目录",comment="用于分类展示")
	@Column(name="category",length=255,nullable=true,unique=false)
	private String category;

	@DColumn(index=6,label="模板类别",comment="报表、图表、混合表")
	@Column(name="model_type",length=255,nullable=true,unique=false)
	private String modelType;

	@DColumn(index=7,label="导出文件名称",comment="默认为模板名称")
	@Column(name="export_name",length=255,nullable=true,unique=false)
	private String exportName;

	@DColumn(index=8,label="导出文件类型",comment="xls、pdf")
	@Column(name="export_file_type",length=255,nullable=true,unique=false)
	private String exportFileType;

	@DColumn(index=9,label="是否分页",comment="是否分页")
	@Column(name="is_pagenation",length=255,nullable=true,unique=false)
	private String isPagenation;

	@DColumn(index=10,label="一页展示条数",comment="分页展示,一页展示的条数")
	@Column(name="display_num",length=255,nullable=true,unique=false)
	private Long displayNum;

	@DColumn(index=11,label="是否添加检索",comment="是否添加检索")
	@Column(name="is_search",length=255,nullable=true,unique=false)
	private String isSearch;


	public String getModelKey() {
		return this.modelKey;
	}

	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getExportName() {
		return this.exportName;
	}

	public void setExportName(String exportName) {
		this.exportName = exportName;
	}

	public String getExportFileType() {
		return this.exportFileType;
	}

	public void setExportFileType(String exportFileType) {
		this.exportFileType = exportFileType;
	}

	public String getIsPagenation() {
		return this.isPagenation;
	}

	public void setIsPagenation(String isPagenation) {
		this.isPagenation = isPagenation;
	}

	public Long getDisplayNum() {
		return this.displayNum;
	}

	public void setDisplayNum(Long displayNum) {
		this.displayNum = displayNum;
	}

	public String getIsSearch() {
		return this.isSearch;
	}

	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}



}