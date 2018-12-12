package com.cisdi.info.simple.dto.attachment;

public class FileUploadDto {

    private String name;
    private String associateFormId;
    private String associateFormName;

    public String getAssociateFormId() {
        return associateFormId;
    }

    public void setAssociateFormId(String associateFormId) {
        this.associateFormId = associateFormId;
    }

    public String getAssociateFormName() {
        return associateFormName;
    }

    public void setAssociateFormName(String associateFormName) {
        this.associateFormName = associateFormName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
