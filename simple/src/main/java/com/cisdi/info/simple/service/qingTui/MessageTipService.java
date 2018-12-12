package com.cisdi.info.simple.service.qingTui;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.cisdi.info.simple.entity.member.Member;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.util.MessageAndEmp;

public interface MessageTipService {
	/**
	 * 发送轻推消息（仅文本）
	 * @param employees 发送对象
	 * @param title 消息头部
	 * @param body 消息内容
	 */
	public boolean leaveMessageOnlyText(List<Employee> employees, String title, String body);

	/**
	 * 发送轻推消息（带链接跳转,但跳转链接固定，慎重选择）
	 * @param employees 发送对象
	 * @param title 消息头部
	 * @param body 消息内容
	 * @param link 跳转路径（如链接150.23.12.11/GBD/gbdFace/index.html?version=5.5#/home/2,您需要传入的链接因该为:home/2）
	 */
	public boolean leaveMessageTextAndLink(List<Employee> employees,String title,String body,String link);
	/**
	 * 发送轻推消息（带单一链接跳转）
	 * @param employees 发送对象
	 * @param paramSet （注意：此参数十分重要，为参数规则，如下：首先它的个数应该与employees匹配，并且一一对应；
	 * 其中集合的map应该包含如下内容：
	 * 1.title:消息头部
	 * 2.body：消息内容
	 * 3.linkTitle:跳转链接的名字，如“查看详情”，轻推消息中点击“查看详情”会跳转至相应的链接
	 * 4.link:跳转的链接（如链接150.23.12.11/GBD/gbdFace/index.html?version=5.5#/home/2,您需要传入的链接因该为:home/2））
	 */
	public boolean leaveMessageTextAndLinkMap(List<Employee> employees,LinkedHashSet<Map<String,String>> paramSet);

	/**
	 * 发送轻推消息给商家（带单一链接跳转）
	 * @param member 发送对象
	 * 2.body：消息内容
	 */
	public boolean leaveMessageToMember(Member member, String body);
	public boolean leaveMessageToEmployee(Employee employee, String body);
	boolean leaveMessageToAllEmployee(List<MessageAndEmp> list);
}