package com.cisdi.info.simple.service.member;


import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.member.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {

    public PageResultDTO findMembers(PageDTO pageDTO);

    public List<Member> findAllMembers();

    public List<Member> findMembersWithIdNameByName(String memberName);

    public List<Member> findAllMembersWithIdName();

    public Member findMembersWithIdNameById(Long memberId);

    public Member findMember(Long memberId);

 /*   public MemberDetailsDto findMemberDetails(Long memberId);*/
    //所有外键的Name都以加载
    public Member findMemberWithForeignName(Long memberId);

    public Member saveMember(Member member);

    public Member updateMember(Member member);

    public void deleteMember(Long memberId);

    public Map<String, List<String>> findAreaAndMemberType();

    public PageResultDTO findMemberByTypeAndArea(Map<String, String> map);
}
