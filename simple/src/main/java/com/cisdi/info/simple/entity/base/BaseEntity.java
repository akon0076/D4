package com.cisdi.info.simple.entity.base;

import com.cisdi.info.simple.annotation.DColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @DColumn(index=0,label="标识",comment="唯一标识")
    @Column(name = "eid",nullable=false,unique=true)
    protected Long EId;

    @DColumn(index=1,label="名称",comment="名称")
    @Column(name = "name",nullable = true,unique=false)
    protected String name;

    @DColumn(index=10000,label="备注",comment="备注")
    @Column(name ="remark" ,nullable = true)
    protected String remark;

    /**
     * 创建人
     */
    @DColumn(index=10001,label="创建人",comment="创建人")
    @Column(name = "create_id",nullable = true)
    protected Long createId;

//    @DColumn(index=10002,label="创建人",comment="创建人")
//    @Transient
//    protected String createCaption;
    /**
     * 创建时间
     */
    @DColumn(index=10003,label="创建时间",comment="创建时间")
    @Column(name = "create_datetime", nullable = true)
    protected Date createDatetime;

    /**
     *  修改人
     */
    @DColumn(index=10004,label="修改人",comment="修改人")
    @Column(name = "update_id",nullable = true)
    protected Long updateId;

//    @DColumn(index=10005,label="修改人",comment="修改人")
//    @Transient
//    protected String updateCaption;
    /**
     * 修改时间
     */
    @DColumn(index=10006,label="修改时间",comment="修改时间")
    @Column(name = "update_datetime",nullable = true)
    protected Date updateDatetime;


    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Long getEId() {
        return EId;
    }

    public void setEId(Long EId) {
        this.EId = EId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    public String getCreateCaption() {
//        return createCaption;
//    }
//
//    public void setCreateCaption(String createCaption) {
//        this.createCaption = createCaption;
//    }
//
//    public String getUpdateCaption() {
//        return updateCaption;
//    }
//
//    public void setUpdateCaption(String updateCaption) {
//        this.updateCaption = updateCaption;
//    }
}
