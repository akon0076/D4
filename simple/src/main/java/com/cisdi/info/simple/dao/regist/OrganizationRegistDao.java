package com.cisdi.info.simple.dao.regist;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.regist.OrganizationRegist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "organizationRegistDao")
public interface OrganizationRegistDao {

    public List<OrganizationRegist> findOrganizationRegists(PageDTO pageDTO);

    public List<OrganizationRegist> findAllOrganizationRegists();

    public List<OrganizationRegist> findAllOrganizationRegistsWithIdName();

    public List<OrganizationRegist> findOrganizationRegistsWithIdNameByName(@Param("organizationRegistName") String organizationRegistName);

    public OrganizationRegist findOrganizationRegistsWithIdNameById(@Param(" organizationRegistId") Long organizationRegistId);

    public Long findOrganizationRegistTotalCount(PageDTO pageDTO);

    public OrganizationRegist findOrganizationRegist(@Param("organizationRegistId") Long organizationRegistId);

    //所有外键的Name都以加载
    public OrganizationRegist findOrganizationRegistWithForeignName(@Param("organizationRegistId") Long organizationRegistId);

    public Integer saveOrganizationRegist(OrganizationRegist organizationRegist);

    public Integer updateOrganizationRegist(OrganizationRegist organizationRegist);

    public Integer deleteOrganizationRegist(@Param("organizationRegistId") Long organizationRegistId);
}
