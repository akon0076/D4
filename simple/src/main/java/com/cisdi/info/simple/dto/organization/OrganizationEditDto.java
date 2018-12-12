package com.cisdi.info.simple.dto.organization;


import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
import com.cisdi.info.simple.entity.organization.Organization;
public class OrganizationEditDto{

private Organization organization;


//外键实体是：Organization
private List<Organization> parentOrganizations;


public  Organization getOrganization()
{
    return this.organization;
}
public  void setOrganization(Organization organization)
{
    this.organization = organization;
}

public List<Organization> getParentOrganizations()
{
    return this.parentOrganizations;
}
public void setParentOrganizations(List<Organization> parentOrganizations)
{
    this.parentOrganizations = parentOrganizations;
}
}
