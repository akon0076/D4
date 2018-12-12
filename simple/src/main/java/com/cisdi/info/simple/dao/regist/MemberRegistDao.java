package com.cisdi.info.simple.dao.regist;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.regist.MemberRegist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "memberRegistDao")
public interface MemberRegistDao {

    public List<MemberRegist> findMemberRegists(PageDTO pageDTO);

    public List<MemberRegist> findAllMemberRegists();

    public List<MemberRegist> findAllMemberRegistsWithIdName();

    public List<MemberRegist> findMemberRegistsWithIdNameByName(@Param("memberRegistName") String memberRegistName);

    public MemberRegist findMemberRegistsWithIdNameById(@Param(" memberRegistId") Long memberRegistId);

    public Long findMemberRegistTotalCount(PageDTO pageDTO);

    public MemberRegist findMemberRegist(@Param("memberRegistId") Long memberRegistId);

    //所有外键的Name都以加载
    public MemberRegist findMemberRegistWithForeignName(@Param("memberRegistId") Long memberRegistId);

    public Integer saveMemberRegist(MemberRegist memberRegist);

    public Integer updateMemberRegist(MemberRegist memberRegist);

    public Integer deleteMemberRegist(@Param("memberRegistId") Long memberRegistId);
}
