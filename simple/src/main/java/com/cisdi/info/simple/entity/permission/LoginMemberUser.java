package com.cisdi.info.simple.entity.permission;


import com.cisdi.info.simple.entity.member.Member;
import com.cisdi.info.simple.entity.organization.Organization;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author 许洲
 * userRoles 此次登陆人对应的角色
 * loginVip 后台登陆 该值为空
 * loginOperator 前台登陆 该值为空
 * userName 登陆人名称
 * userPermissionsCode 登陆人所有的权限编码
 */
public class LoginMemberUser implements Serializable{

	private static final long serialVersionUID = 4510107943850149711L;

	// 后台角色
	private List<Long> userRoles;

	private List<String> userPermissionsCode;

	private Operator loginOperator;

	private Member loginMember;

	public Member getLoginMember() {
		return loginMember;
	}

	public void setLoginMember(Member loginMember) {
		this.loginMember = loginMember;
	}

	private String userName;

	private Organization currentOrganization;

	public List<Long> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Long> userRoles) {
		this.userRoles = userRoles;
	}

	public List<String> getUserPermissionsCode() {
		return userPermissionsCode;
	}

	public void setUserPermissionsCode(List<String> userPermissionsCode) {
		this.userPermissionsCode = userPermissionsCode;
	}

	public Operator getLoginOperator() {
		return loginOperator;
	}

	public void setLoginOperator(Operator loginOperator) {
		this.loginOperator = loginOperator;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Organization getCurrentOrganization() {
		return currentOrganization;
	}

	public void setCurrentOrganization(Organization currentOrganization) {
		this.currentOrganization = currentOrganization;
	}


}
