

package com.cisdi.info.simple.controller.regist;

import com.cisdi.info.simple.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.regist.MemberRegistEditDto;
import com.cisdi.info.simple.entity.regist.MemberRegist;
import com.cisdi.info.simple.service.regist.MemberRegistService;



/**module
{
"simple/regist/MemberRegist": {
"code": "simple/regist/MemberRegist",
"name1": "商家注册审核",
"url": "/simple/regist/MemberRegist",
"route": "/simple/regist/MemberRegist",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/regist",
"parentName": "商家注册审核",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_regist_MemberRegist_Add",
	"name1": "新增",
	"fullName": "simple.商家注册审核.商家注册审核.新增",
	"moduleCode": "simple/regist/MemberRegist",
	urls:[
		"/simple/regist/MemberRegist/createMemberRegist",
		"/simple/regist/MemberRegist/saveMemberRegist"
	]
	},
	{
	"code": "simple_regist_MemberRegist_Edit",
	"name1": "编辑",
	"fullName": "simple.商家注册审核.商家注册审核.编辑",
	"moduleCode": "simple/regist/MemberRegist",
	urls:[
		"/simple/regist/MemberRegist/findMemberRegistForEdit",
		"/simple/regist/MemberRegist/updateMemberRegist"
	]
	},
	{
	"code": "simple_regist_MemberRegist_Delete",
	"name1": "删除",
	"fullName": "simple.商家注册审核.商家注册审核.删除",
	"moduleCode": "simple/regist/MemberRegist",
	urls:[
	"/simple/regist/MemberRegist/deleteMemberRegist"
	]
	},
	{
	"code": "simple_regist_MemberRegist_View",
	"name1": "查看",
	"fullName": "simple.商家注册审核.商家注册审核.查看",
	"moduleCode": "simple/regist/MemberRegist",
	urls:[
	"/simple/regist/MemberRegist/findMemberRegists",
	"/simple/regist/MemberRegist/findMemberRegistForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/regist/MemberRegist")
@CrossOrigin(allowCredentials = "true")
public class MemberRegistController {
	private static Logger logger = LogManager.getLogger();



	@Autowired private MemberRegistService memberRegistService;

	@PostMapping("/findMemberRegists")
	public PageResultDTO findMemberRegists(@RequestBody PageDTO pageDTO){
		return this.memberRegistService.findMemberRegists(pageDTO);
	}

	@GetMapping("/findMemberRegist")
	public MemberRegist findMemberRegist(@RequestParam Long memberRegistId)
	{
		return this.memberRegistService.findMemberRegist(memberRegistId);
	}

	@GetMapping("/findMemberRegistForView")
	public MemberRegist findMemberRegistForView(@RequestParam Long memberRegistId)
	{
		return this.memberRegistService.findMemberRegistWithForeignName(memberRegistId);
	}

	@GetMapping("/findMemberRegistForEdit")
	public MemberRegistEditDto findMemberRegistForEdit(@RequestParam Long memberRegistId)
	{
		MemberRegistEditDto memberRegistEditDto = new MemberRegistEditDto();
		memberRegistEditDto.setMemberRegist(this.memberRegistService.findMemberRegistWithForeignName(memberRegistId));

		this.prepareMemberRegistEditDto(memberRegistEditDto);

		return memberRegistEditDto;
	}

	//创建新的商家注册审核
	@GetMapping("/createMemberRegist")
	public MemberRegistEditDto createMemberRegist()
	{
		MemberRegistEditDto memberRegistEditDto = new MemberRegistEditDto();
		memberRegistEditDto.setMemberRegist(new MemberRegist());

		this.prepareMemberRegistEditDto(memberRegistEditDto);
		return memberRegistEditDto;
	}

	private void prepareMemberRegistEditDto(MemberRegistEditDto memberRegistEditDto)
	{
	}

	@PostMapping("/saveMemberRegist")
	public MemberRegist saveMemberRegist(@RequestBody MemberRegist memberRegist)
	{
		return this.memberRegistService.saveMemberRegist(memberRegist);
	}

	@PostMapping("/updateMemberRegist")
	public MemberRegist updateMemberRegist(@RequestBody MemberRegist memberRegist)
	{
		return this.memberRegistService.updateMemberRegist(memberRegist);
	}

	@GetMapping("/deleteMemberRegist")
	public void deleteMemberRegist(@RequestParam Long memberRegistId)
	{
		this.memberRegistService.deleteMemberRegist(memberRegistId);
	}
	@GetMapping("/findMemberRegistsWithIdNameById")
	public MemberRegist findMemberRegistsWithIdNameById(@RequestParam Long memberRegistId)
	{
		return this.memberRegistService.findMemberRegistsWithIdNameById(memberRegistId);
	}

	@GetMapping("/findMemberRegistsWithIdNameByName")
	public List<MemberRegist> findMemberRegistsWithIdNameByName(String memberRegistName)
	{
		return this.memberRegistService.findMemberRegistsWithIdNameByName(memberRegistName);
	}
}

