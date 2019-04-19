package com.cisdi.info.simple.service.qingTui.impl;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.config.NudgePlusConfig;
import com.cisdi.info.simple.dao.permission.OperatorDao;
import com.cisdi.info.simple.dao.qingTui.EmpOpenIdDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.operator.LoginDTO;
import com.cisdi.info.simple.dto.qingTui.BindingDto;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.entity.member.Member;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.qingTui.EmpOpenId;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.member.MemberService;
import com.cisdi.info.simple.service.organization.impl.EmployeeServiceBean;
import com.cisdi.info.simple.service.permission.OperatorService;
import com.cisdi.info.simple.service.qingTui.EmpOpenIdService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EmpOpenIdServiceBean extends BaseService implements EmpOpenIdService {

	private static Logger logger = LogManager.getLogger();

	String basePath = NudgePlusConfig.getValue("basePath");
	HttpServletResponse response;
	@Autowired
	private EmpOpenIdDao empOpenIdDao;

	@Autowired
	private OperatorService operatorService;

	@Autowired
	private OperatorDao operatorDao;

	@Autowired
	private EmployeeServiceBean employeeService;

	@Autowired
	private MemberService memberService;

	public PageResultDTO findEmpOpenIds(PageDTO pageDTO)
	{
		pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<EmpOpenId> empOpenIdDTOS = this.empOpenIdDao.findEmpOpenIds(pageDTO);
		Long totalCount = this.empOpenIdDao.findEmpOpenIdTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(empOpenIdDTOS);

		return pageResultDTO;
	}

	public List<EmpOpenId> findAllEmpOpenIds()
	{
		return this.empOpenIdDao.findAllEmpOpenIds();
	}

	public List<EmpOpenId> findAllEmpOpenIdsWithIdName()
	{
		return this.empOpenIdDao.findAllEmpOpenIdsWithIdName();
	}

	public List<EmpOpenId> findEmpOpenIdsWithIdNameByName(String empOpenIdName)
	{
		return this.empOpenIdDao.findEmpOpenIdsWithIdNameByName(empOpenIdName);
	}

	public EmpOpenId findEmpOpenIdsWithIdNameById(Long empOpenIdId)
	{
		return this.empOpenIdDao.findEmpOpenIdsWithIdNameById(empOpenIdId);
	}

	public EmpOpenId findEmpOpenId(Long empOpenIdId)
	{
		return this.empOpenIdDao.findEmpOpenId(empOpenIdId);
	}

	//所有外键的Name都以加载
	public EmpOpenId findEmpOpenIdWithForeignName(Long empOpenIdId)
	{
		return this.empOpenIdDao.findEmpOpenIdWithForeignName(empOpenIdId);
	}

	public EmpOpenId saveEmpOpenId(EmpOpenId empOpenId)
	{
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(empOpenId);
		Integer rows = this.empOpenIdDao.saveEmpOpenId(empOpenId);
		if(rows != 1)
		{
			String error = "新增保存人员与应用出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return empOpenId;
	}

	public EmpOpenId updateEmpOpenId(EmpOpenId empOpenId)
	{
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(empOpenId);
		Integer rows = this.empOpenIdDao.updateEmpOpenId(empOpenId);
		if(rows != 1)
		{
			String error = "修改保存人员与应用出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return empOpenId;
	}

	public void deleteEmpOpenId(Long empOpenIdId)
	{
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(EmpOpenId.class, empOpenIdId);
		if(entityUsageMap != null && entityUsageMap.size() >0)
		{
			StringBuilder errors = new StringBuilder();
			errors.append("计划删除的数据正在被以下数引用\n");
			for(EntityUsage entityUsage : entityUsageMap.values())
			{
				errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
				for(Map.Entry<Long,String> entry : entityUsage.getUsageIdNames().entrySet() )
				{
errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
}
			}
			errors.append("，不能删除，请检查处理后再删除");
			throw  new DDDException(errors.toString());
		}

		Integer rows = this.empOpenIdDao.deleteEmpOpenId(empOpenIdId);
		if(rows != 1)
		{
			String error = "删除人员与应用出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}

	/**
	 * 绑定用户
	 */
	public Map<String,Object> bindingQingtui(BindingDto bindingDto){
		try{
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUserName(bindingDto.getName());
			loginDTO.setPassword(DigestUtils.md5DigestAsHex(bindingDto.getPassword().getBytes()));
			Map<String,Object> result = this.operatorService.checkOperatorByUserNameAndPassWord(loginDTO);//检查用户名和密码
			Boolean isCheck = Boolean.parseBoolean(result.get("isLogin").toString());
			if(isCheck) {
				Operator operator = this.operatorDao.findOperatorByCode(bindingDto.getName());
				EmpOpenId empOpenId = this.findEmpOpenIdByEmpIdAndOpenId(operator.getPersonId(),bindingDto.getOpenId());

				if(empOpenId != null) {
					return this.dealMap(false, "该用户已经绑定");
				}

				//新增绑定
                EmpOpenId newEmpOpenId = new EmpOpenId();
                newEmpOpenId.setUId(bindingDto.getuId());
                newEmpOpenId.setOpenId(bindingDto.getOpenId());
                newEmpOpenId.setOperatorId(operator.getEId());
                newEmpOpenId.setUserType(result.get("type").toString());

				if(result.get("type").equals("职员")){
					Employee employee = this.employeeService.findEmployee(operator.getPersonId());
					newEmpOpenId.setEmployee(employee);
					this.saveEmpOpenId(newEmpOpenId);

					response.sendRedirect(this.basePath + "main/" + operator.getEId()+"/"+ URLEncoder.encode(employee.getName(),"UTF-8"));
					return this.dealMap(true, employee.getEId().toString());
				}
				else{
					//绑定Member
					Member member = this.memberService.findMember(operator.getPersonId());
					newEmpOpenId.setMember(member);
					this.saveEmpOpenId(newEmpOpenId);

					response.sendRedirect(this.basePath + "main/" + operator.getEId()+"/"+ URLEncoder.encode(member.getName(),"UTF-8"));
					return this.dealMap(true, member.getEId().toString());
				}


				//模拟登陆
//				this.operatorService.checkLoginUser(employee.getEId(), employee.getOrganization().getEId());
//				 this.dealMap(true, employee.getEId().toString());

			}
			else {
				return this.dealMap(false, "用户名或密码错误");
			}

		} catch (Exception e) {
			return this.dealMap(false, "绑定异常"+e.getMessage());
		}

	}

	public EmpOpenId findEmpOpenIdByEmpIdAndOpenId(Long employeeId, String openId) {
        EmpOpenId empOpenId = new EmpOpenId();
        empOpenId.setEmployeeId(employeeId);
        empOpenId.setOpenId(openId);
		return this.empOpenIdDao.findEmpOpenIdByEmpIdAndOpenId(empOpenId);
	}

	public Map<String,Object> dealMap(boolean returnState,String returnRemark) {
		Map<String,Object>  map = new HashMap<String, Object>();
		map.put("returnState", returnState);//是否成功
		map.put("returnRemark", returnRemark);//说明
		return map;
	}

	public EmpOpenId findEmpOpenIdByEmpIdAndType(Long employeeId,String type){
		EmpOpenId empOpenId = new EmpOpenId();
		empOpenId.setEmployeeId(employeeId);
		empOpenId.setType(type);
		return this.empOpenIdDao.findEmpOpenIdByEmpIdAndType(empOpenId);
	}

	public EmpOpenId findMemberOpenIdByMemIdAndType(Long memberId,String type){
		EmpOpenId empOpenId = new EmpOpenId();
		empOpenId.setMemberId(memberId);
		empOpenId.setType(type);
		return this.empOpenIdDao.findMemberOpenIdByMemIdAndType(empOpenId);
	}
}
