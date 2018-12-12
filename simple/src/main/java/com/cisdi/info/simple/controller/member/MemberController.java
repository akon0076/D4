

package com.cisdi.info.simple.controller.member;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;

import com.cisdi.info.simple.entity.member.Member;
import com.cisdi.info.simple.service.member.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/simple/member/Member")
@CrossOrigin(allowCredentials = "true")
public class MemberController {
	private static Logger logger = LogManager.getLogger();



	@Autowired private MemberService memberService;

	@PostMapping("/findMembers")
	public PageResultDTO findMembers(@RequestBody PageDTO pageDTO){
 		return this.memberService.findMembers(pageDTO);
	}

/*
	@GetMapping("/findMemberDetails")
	public MemberDetailsDto findMemberDetails(@RequestParam Long memberId){
 		return this.memberService.findMemberDetails(memberId);
	}
*/


	@GetMapping("/findMember")
	public Member findMember(@RequestParam Long memberId)
	{
		return this.memberService.findMember(memberId);
	}

	@GetMapping("/findMemberForView")
	public Member findMemberForView(@RequestParam Long memberId)
	{
		return this.memberService.findMemberWithForeignName(memberId);
	}

/*	@GetMapping("/findMemberForEdit")
	public MemberEditDto findMemberForEdit(@RequestParam Long memberId)
	{
		MemberEditDto memberEditDto = new MemberEditDto();
		memberEditDto.setMember(this.memberService.findMemberWithForeignName(memberId));

		this.prepareMemberEditDto(memberEditDto);

		return memberEditDto;
	}*/

/*	//创建新的商户
	@GetMapping("/createMember")
	public MemberEditDto createMember()
	{
		MemberEditDto memberEditDto = new MemberEditDto();
		memberEditDto.setMember(new Member());

		this.prepareMemberEditDto(memberEditDto);
		return memberEditDto;
	}

	private void prepareMemberEditDto(MemberEditDto memberEditDto)
	{
	}*/

	@PostMapping("/saveMember")
	public Member saveMember(@RequestBody Member member)
	{
		return this.memberService.saveMember(member);
	}

	@PostMapping("/updateMember")
	public Member updateMember(@RequestBody Member member)
	{
		return this.memberService.updateMember(member);
	}

	@GetMapping("/deleteMember")
	public void deleteMember(@RequestParam Long memberId)
	{
		this.memberService.deleteMember(memberId);
	}
	@GetMapping("/findMembersWithIdNameById")
		public Member findMembersWithIdNameById(@RequestParam Long memberId)
	{
		return this.memberService.findMembersWithIdNameById(memberId);
	}

	@GetMapping("/findMembersWithIdNameByName")
	public List<Member> findMembersWithIdNameByName(String memberName)
	{
		return this.memberService.findMembersWithIdNameByName(memberName);
	}

	@GetMapping("/findAreaAndMemberType")
	public Map<String,List<String>> findAreaAndMemberType(){
		return this.memberService.findAreaAndMemberType();
	}

	@PostMapping("/findMemberByTypeAndArea")
	public PageResultDTO findMemberByTypeAndArea(@RequestBody Map<String, String> map){
		return this.memberService.findMemberByTypeAndArea(map);
	}
}

