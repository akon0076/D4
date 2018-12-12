package com.cisdi.info.simple.service.organization;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.organization.Organization;

import java.util.List;

public interface OrganizationService {

    public PageResultDTO findOrganizations(PageDTO pageDTO);

    public List<Organization> findAllOrganizations();

    public List<Organization> findOrganizationsWithIdNameByName(String organizationName);

    public List<Organization> findAllOrganizationsWithIdName();

    public Organization findOrganizationsWithIdNameById(Long organizationId);

    public Organization findOrganization(Long organizationId);

    //所有外键的Name都以加载
    public Organization findOrganizationWithForeignName(Long organizationId);

    public Organization saveOrganization(Organization organization);

    public Organization updateOrganization(Organization organization);

    public void deleteOrganization(Long organizationId);
}
