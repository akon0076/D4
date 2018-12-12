package com.cisdi.info.simple.service.member.impl;


import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dao.member.MemberDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.entity.member.Member;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.member.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MemberServiceBean extends BaseService implements MemberService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private MemberDao memberDao;

	/*@Autowired
	private CommodityTypeDao commodityTypeDao;

	@Autowired
	private CommodityDao commodityDao;*/


	public PageResultDTO findMembers(PageDTO pageDTO)
	{
		pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<Member> memberDTOS = this.memberDao.findMembers(pageDTO);
		Long totalCount = this.memberDao.findMemberTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(memberDTOS);

		return pageResultDTO;
	}

	public List<Member> findAllMembers()
	{
		return this.memberDao.findAllMembers();
	}

	public List<Member> findAllMembersWithIdName()
	{
		return this.memberDao.findAllMembersWithIdName();
	}

	public List<Member> findMembersWithIdNameByName(String memberName)
	{
		return this.memberDao.findMembersWithIdNameByName(memberName);
	}

	public Member findMembersWithIdNameById(Long memberId)
	{
		return this.memberDao.findMembersWithIdNameById(memberId);
	}

	public Member findMember(Long memberId)
	{
		return this.memberDao.findMember(memberId);
	}
	/**
	 * @auther: $郑云$
	 * @date: 2018/8/10 16:32
	 * @describe:
	 */
	/*@Override
	public MemberDetailsDto findMemberDetails(Long memberId) {
		MemberDetailsDto memberDetailsDto=new MemberDetailsDto();
		Member member = this.memberDao.findMember(memberId);
		memberDetailsDto.setMember(member);
		List<CommodityType> commodityTypes=this.commodityTypeDao.findCommodityTypesByMemberId(memberId);
		List<CommodityTypeIncludeCommodityDto> commodityTypeIncludeCommodityDtos=new ArrayList<CommodityTypeIncludeCommodityDto>();
		for(CommodityType item : commodityTypes ){
			CommodityTypeIncludeCommodityDto commodityTypeIncludeCommodityDto = new CommodityTypeIncludeCommodityDto();
			commodityTypeIncludeCommodityDto.setName(item.getName());
			commodityTypeIncludeCommodityDto.setType(item.getEId());
			List<Commodity> commoditys = new ArrayList<Commodity>();
			commoditys = this.commodityDao.findCommodityByCommodityTypeId(item.getEId());
			commodityTypeIncludeCommodityDto.setCommoditys(commoditys);
			commodityTypeIncludeCommodityDtos.add(commodityTypeIncludeCommodityDto);
		}
		memberDetailsDto.setCommodityTypeIncludeCommodityDtos(commodityTypeIncludeCommodityDtos);
		return memberDetailsDto;
	}*/

	//所有外键的Name都以加载
	public Member findMemberWithForeignName(Long memberId)
	{
		return this.memberDao.findMemberWithForeignName(memberId);
	}

	public Member saveMember(Member member)
	{
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(member);
		Integer rows = this.memberDao.saveMember(member);
		if(rows != 1)
		{
			String error = "新增保存商户出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return member;
	}

	public Member updateMember(Member member)
	{
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(member);
		Integer rows = this.memberDao.updateMember(member);
		if(rows != 1)
		{
			String error = "修改保存商户出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return member;
	}

	public void deleteMember(Long memberId)
	{
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(Member.class, memberId);
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

		Integer rows = this.memberDao.deleteMember(memberId);
		if(rows != 1)
		{
			String error = "删除商户出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}

	public Map<String, List<String>> findAreaAndMemberType(){
		List<String> area = this.memberDao.findArea();
		List<String> memberType = this.memberDao.findMemberType();
		Map<String, List<String>> map = new HashMap<>();
		map.put("area", area);
		map.put("memberType", memberType);
		return map;
	}

	public PageResultDTO findMemberByTypeAndArea(Map<String,String> map)
	{
		String memberType = map.get("memberType");
		String area = map.get("area");
		String sort = map.get("sort");
		Integer currentPage = Integer.valueOf(map.get("currentPage"));
		Integer pageSize = Integer.valueOf(map.get("pageSize"));
		Integer startIndex = (currentPage - 1) * pageSize;
		List<Member> memberDTOS = this.memberDao.findMemberByTypeAndArea(memberType,area,sort,startIndex,pageSize);
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPageSize(pageSize);
		pageDTO.setCurrentPage(currentPage);
		pageDTO.setStartIndex(startIndex);
		Long totalCount = this.memberDao.findMemberTotalCount(pageDTO);
		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(memberDTOS);

		return pageResultDTO;
	}

}
