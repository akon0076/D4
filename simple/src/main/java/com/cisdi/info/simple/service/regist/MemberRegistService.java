package com.cisdi.info.simple.service.regist;

import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.regist.MemberRegist;
import com.cisdi.info.simple.dto.base.PageDTO;

import java.util.List;

public interface MemberRegistService {

    public PageResultDTO findMemberRegists(PageDTO pageDTO);

    public List<MemberRegist> findAllMemberRegists();

    public List<MemberRegist> findMemberRegistsWithIdNameByName(String memberRegistName);

    public List<MemberRegist> findAllMemberRegistsWithIdName();

    public MemberRegist findMemberRegistsWithIdNameById(Long memberRegistId);

    public MemberRegist findMemberRegist(Long memberRegistId);

    //所有外键的Name都以加载
    public MemberRegist findMemberRegistWithForeignName(Long memberRegistId);

    public MemberRegist saveMemberRegist(MemberRegist memberRegist);

    public MemberRegist updateMemberRegist(MemberRegist memberRegist);

    public void deleteMemberRegist(Long memberRegistId);
}
