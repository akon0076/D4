package com.cisdi.info.simple.dao.organization;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.organization.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component(value = "organizationDao")
public interface OrganizationDao {
    public List<HashMap<String,String>> findAllForeignKeys(java.util.HashMap<String,String> hashMap);
    public List<Organization> findOrganizations(PageDTO pageDTO);

    public List<Organization> findAllOrganizations();

    public List<Organization> findAllOrganizationsWithIdName();

    public List<Organization> findOrganizationsWithIdNameByName(@Param("organizationName") String organizationName);

    public Organization findOrganizationsWithIdNameById(Long organizationId);

    public Long findOrganizationTotalCount(PageDTO pageDTO);

    public Organization findOrganization(@Param("organizationId") Long organizationId);

    //所有外键的Name都以加载
    public Organization findOrganizationWithForeignName(@Param("organizationId") Long organizationId);

    public Integer saveOrganization(Organization organization);

    public Integer updateOrganization(Organization organization);

    public Integer deleteOrganization(@Param("organizationId") Long organizationId);

    public Organization findOrganizationByName(String organizationName);
}
