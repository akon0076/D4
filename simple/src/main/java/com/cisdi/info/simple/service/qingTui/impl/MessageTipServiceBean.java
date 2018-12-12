package com.cisdi.info.simple.service.qingTui.impl;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.cisdi.info.simple.entity.member.Member;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.qingTui.EmpOpenId;
import com.cisdi.info.simple.service.organization.EmployeeService;
import com.cisdi.info.simple.util.Config;
import com.cisdi.info.simple.util.MessageAndEmp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisdi.nudgeplus.sdk.service.SingleMessageService;
import com.cisdi.nudgeplus.sdk.service.TokenService;
import com.cisdi.nudgeplus.tmsbeans.model.RichMsg;
import com.cisdi.nudgeplus.tmsbeans.model.RichUrl;
import com.cisdi.nudgeplus.tmsbeans.model.TextMsg;
import com.cisdi.info.simple.service.qingTui.MessageTipService;
import org.springframework.transaction.annotation.Transactional;
import com.cisdi.info.simple.util.NudgePlusConfig;
@Service
@Transactional
public class MessageTipServiceBean implements MessageTipService {

	private static Logger logger = LogManager.getLogger();
	public String appId="";
	private Date date;
	@Autowired
	EmpOpenIdServiceBean empOpenIdService;
	@Autowired
	EmployeeService employeeService;
	public boolean leaveMessageOnlyText(List<Employee> employees, String title, String body) {
		if(employees == null || employees.size() == 0) {
			return false;
		}
		//遍历发送人
		Iterator<Employee> employeesIterator = employees.iterator();
		while(employeesIterator.hasNext()){
			Employee employee = employeesIterator.next();

			String appId = NudgePlusConfig.getValue("APP_ID");
			String appSercret = NudgePlusConfig.getValue("APP_SECRET");
			//刷新acess_token
			TokenService.refreshToken(appId, appSercret);
			//查询发送的openid...
			EmpOpenId empOpenId = this.empOpenIdService.findEmpOpenIdByEmpIdAndType(employee.getEId(),"轻推");

			String logString = "发送提醒消息,"+employee.getName()+",";
			if(empOpenId.getOpenId() == null || empOpenId.getOpenId().equals("")) {
				return false;
			}
			else{
				logString += "openid="+empOpenId.getOpenId();
				//配置消息内容
				RichMsg rm = this.createRichMsg(title, body, null, null);
				try 
				{
					SingleMessageService.sendRichMsg(empOpenId.getOpenId(), rm);
					logString += ",发送成功!";
				} 
				catch (Exception e) 
				{
					logString += ",发送失败,原因是："+e.getMessage();
				}
			}

			String  logFileName = Config.logPath + "\\"+logString +".txt";
			System.out.println(logFileName);
			PrintWriter printWriter;
			try {
				printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFileName)),true);
				printWriter.println(logFileName);
				printWriter.println("");
				printWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean leaveMessageTextAndLink(List<Employee> employees, String title, String body, String link) {
		if(employees == null || employees.size() == 0) {
			return false;
		}

		Iterator<Employee> employeesIterator = employees.iterator();
		while(employeesIterator.hasNext()){
			Employee employee = employeesIterator.next();

			String appId = NudgePlusConfig.getValue("APP_ID");
			String appSercret = NudgePlusConfig.getValue("APP_SECRET");

			TokenService.refreshToken(appId,appSercret);

			EmpOpenId empOpenId = this.empOpenIdService.findEmpOpenIdByEmpIdAndType(employee.getEId(),"轻推");

			String logString = "发送提醒消息,"+employee.getName()+",";
			if(empOpenId.getOpenId() == null || empOpenId.getOpenId().equals("")) {
				return false;
			}
			else{
				logString += "openid="+empOpenId.getOpenId();
				RichMsg rm = this.createRichMsg(title, body, "查看详情", link);
				try
				{
					SingleMessageService.sendRichMsg(empOpenId.getOpenId(), rm);
					logString += ",发送成功!";
				}
				catch (Exception e)
				{
					logString += ",发送失败,原因是："+e.getMessage();
				}
			}

			String  logFileName = Config.logPath + "\\"+logString +".txt";
			System.out.println(logFileName);
			PrintWriter printWriter;
			try {
				printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFileName)),true);
				printWriter.println(logFileName);
				printWriter.println("");
				printWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean leaveMessageTextAndLinkMap(List<Employee> employees, LinkedHashSet<Map<String, String>> paramSet) {
		if(employees == null || employees.size() == 0 || paramSet == null || paramSet.size() == 0 || paramSet.size() != employees.size()) {
			return false;
		}

		String basePath = NudgePlusConfig.getValue("basePath");

		Iterator<Map<String, String>> paramSetIterator = paramSet.iterator();
		Iterator<Employee> employeesIterator = employees.iterator();
		boolean isRefresh = true;
		while(employeesIterator.hasNext() && paramSetIterator.hasNext()){
			Employee employee = employeesIterator.next();
			Map<String,String> params = paramSetIterator.next();

			if(isRefresh == true)
			{
				String appId = NudgePlusConfig.getValue("APP_ID");
				String appSercret = NudgePlusConfig.getValue("APP_SECRET");
				TokenService.refreshToken(appId, appSercret);
				isRefresh = false;
			}


			EmpOpenId empOpenId = this.empOpenIdService.findEmpOpenIdByEmpIdAndType(employee.getEId(),"轻推");

			String logString = "发送提醒消息,"+employee.getName()+",";
			if(empOpenId == null || empOpenId.getOpenId() == null || empOpenId.getOpenId().equals("")) {
				return false;
			}
			else{
				logString += "openid="+empOpenId.getOpenId();
				String pushUrl =  basePath+"main" + params.get("link");
				RichMsg rm = new RichMsg();
				rm.setTitle(params.get("title"));
				rm.setBody(params.get("body"));
				List<RichUrl> urls = new ArrayList<RichUrl>();
				RichUrl url = new RichUrl();
				url.setName(params.get("linkTitle"));
				url.setUrl(pushUrl);
				urls.add(url);
				rm.setUrlList(urls);
				try
				{
					SingleMessageService.sendRichMsg(empOpenId.getOpenId(), rm);
					logString += ",发送成功!";
				}
				catch (Exception e)
				{
					logString += ",发送失败,原因是："+e.getMessage();
				}
			}

			String  logFileName = Config.logPath + "\\"+logString +".txt";
			System.out.println(logFileName);
			PrintWriter printWriter;
			try {
				printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFileName)),true);
				printWriter.println(logFileName);
				printWriter.println("");
				printWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean leaveMessageToMember(Member member, String body) {
		if(member == null) {
			return false;
		}
		String appId = NudgePlusConfig.getValue("APP_ID");
		String appSercret = NudgePlusConfig.getValue("APP_SECRET");
		//刷新acess_token
		if(date ==null|| new Date().getTime()-date.getTime() > 60000)
		{
			TokenService.refreshToken(appId, appSercret);
			date = new Date();
		}
		//查询发送的openid...
		EmpOpenId memberOpenId = this.empOpenIdService.findMemberOpenIdByMemIdAndType(member.getEId(),"轻推");

		String  logString = "";
		if(memberOpenId == null || memberOpenId.getOpenId() == null || memberOpenId.getOpenId().equals("")) {
			return false;
		}
		else{
			logString = "发送提醒消息,"+member.getMemberName()+",";
			logString += "openid="+memberOpenId.getOpenId();
			//配置消息内容
			TextMsg rm = this.createTextMsg(body);
			try
			{
				SingleMessageService.sendTextMsg(memberOpenId.getOpenId(), rm);
				logString += ",发送成功!";
			}
			catch (Exception e)
			{
				logString += ",发送失败,原因是："+e.getMessage();
			}
		}

		String  logFileName = Config.logPath + "\\"+logString +".txt";
		System.out.println(logFileName);
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFileName)),true);
			printWriter.println(logFileName);
			printWriter.println("");
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean leaveMessageToEmployee(Employee employee, String body) {
		if(employee == null) {
			return false;
		}
		//刷新acess_token
		if(date ==null||new Date().getTime()-date.getTime() > 60000) {
			String appId = NudgePlusConfig.getValue("APP_ID");
			String appSercret = NudgePlusConfig.getValue("APP_SECRET");
			TokenService.refreshToken(appId,appSercret);
			date = new Date();
		}

		//查询发送的openid...
		EmpOpenId empOpenId = this.empOpenIdService.findEmpOpenIdByEmpIdAndType(employee.getEId(), "轻推");

		String  logString = "";
		if(empOpenId == null || empOpenId.getOpenId() == null || empOpenId.getOpenId().equals("")) {
			return false;
		}
		else{
			logString = "发送提醒消息,"+employee.getName()+",";
			logString += "openid="+empOpenId.getOpenId();
			//配置消息内容
			TextMsg rm = this.createTextMsg(body);
			try
			{
				SingleMessageService.sendTextMsg(empOpenId.getOpenId(), rm);
				logString += ",发送成功!";
			}
			catch (Exception e)
			{
				logString += ",发送失败,原因是："+e.getMessage();
			}
		}

		String  logFileName = Config.logPath + "\\"+logString +".txt";
		System.out.println(logFileName);
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFileName)),true);
			printWriter.println(logFileName);
			printWriter.println("");
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;


	}
	public boolean leaveMessageToAllEmployee(List<MessageAndEmp> list) {

		for(MessageAndEmp messageAndEmp : list){
			//刷新acess_token
			if(date ==null||new Date().getTime()-date.getTime() > 60000) {
				String appId = NudgePlusConfig.getValue("APP_ID");
				String appSercret = NudgePlusConfig.getValue("APP_SECRET");
				TokenService.refreshToken(appId,appSercret);
				date = new Date();
			}

			Employee employee = this.employeeService.findEmployee(messageAndEmp.getEmployeeId());
			if(employee == null) {
				return false;
			}
			EmpOpenId empOpenId = this.empOpenIdService.findEmpOpenIdByEmpIdAndType(employee.getEId(),"轻推");
			String  logString = "";
			if(empOpenId == null || empOpenId.getOpenId() == null || empOpenId.getOpenId().equals("")) {
				continue;
			}
			else{
				logString = "发送提醒消息,"+employee.getName()+",";
				logString += "openid="+empOpenId.getOpenId();
				//配置消息内容
				TextMsg rm = this.createTextMsg(messageAndEmp.getMessage());
				try
				{
					SingleMessageService.sendTextMsg(empOpenId.getOpenId(), rm);
					logString += ",发送成功!";
				}
				catch (Exception e)
				{
					logString += ",发送失败,原因是："+e.getMessage();
				}
			}

			String  logFileName = Config.logPath + "\\"+logString +".txt";
			System.out.println(logFileName);
		}
		return false;
	}

	private TextMsg createTextMsg(String text)
	{
		TextMsg textMsg = new TextMsg();
		textMsg.setContent(text);

		return textMsg;
	}

	private RichMsg createRichMsg(String title, String body, String linkTitle,String link)
	{
		String basePath = NudgePlusConfig.getValue("basePath");

		RichMsg rm = new RichMsg();

		rm.setTitle(title);
		rm.setBody(body);

		if(linkTitle != null && link != null)
		{
			List<RichUrl> urls = new ArrayList<RichUrl>();
			RichUrl url = new RichUrl();
			url.setName(linkTitle);
			String pushUrl =  basePath+"main" + link;
			url.setUrl(pushUrl);

			urls.add(url);
			rm.setUrlList(urls);
		}

		return rm;
	}

}
