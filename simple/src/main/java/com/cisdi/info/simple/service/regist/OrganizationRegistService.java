package com.cisdi.info.simple.service.regist;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.regist.OrganizationRegist;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrganizationRegistService {

    public PageResultDTO findOrganizationRegists(PageDTO pageDTO);

    public List<OrganizationRegist> findAllOrganizationRegists();

    public List<OrganizationRegist> findOrganizationRegistsWithIdNameByName(String organizationRegistName);

    public List<OrganizationRegist> findAllOrganizationRegistsWithIdName();

    public OrganizationRegist findOrganizationRegistsWithIdNameById(Long organizationRegistId);

    public OrganizationRegist findOrganizationRegist(Long organizationRegistId);

    //所有外键的Name都以加载
    public OrganizationRegist findOrganizationRegistWithForeignName(Long organizationRegistId);

    public OrganizationRegist saveOrganizationRegist(OrganizationRegist organizationRegist);

    public OrganizationRegist updateOrganizationRegist(HttpServletRequest request, OrganizationRegist organizationRegist);

    public void deleteOrganizationRegist(Long organizationRegistId);
}
