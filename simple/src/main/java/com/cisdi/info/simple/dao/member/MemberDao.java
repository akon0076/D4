package com.cisdi.info.simple.dao.member;


import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.operator.LoginDTO;
import com.cisdi.info.simple.entity.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;



@Mapper
@Component(value = "memberDao")
public interface MemberDao {

    public List<Member> findMembers(PageDTO pageDTO);

    public List<Member> findAllMembers();

    public List<Member> findAllMembersWithIdName();

    public List<Member> findMembersWithIdNameByName(@Param("memberName") String memberName);

    public Member findMembersWithIdNameById(@Param(" memberId") Long memberId);

    public Long findMemberTotalCount(PageDTO pageDTO);

    public Member findMember(@Param("memberId") Long memberId);

   /* public MemberDetailsDto findMemberDetails(@Param("memberId") Long memberId);*/

    //所有外键的Name都以加载
    public Member findMemberWithForeignName(@Param("memberId") Long memberId);

    public Integer saveMember(Member member);

    public Integer updateMember(Member member);

    public Integer deleteMember(@Param("memberId") Long memberId);


     public Member findMemberByUserNameAndPassWord(LoginDTO loginDTO);

    public List<String> findArea();

    public List<String> findMemberType();

    public List<Member> findMemberByTypeAndArea(@Param("memberType") String memberType, @Param("area") String area, @Param("sort") String sort, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

}
