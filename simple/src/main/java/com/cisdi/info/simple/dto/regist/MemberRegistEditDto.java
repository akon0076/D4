package com.cisdi.info.simple.dto.regist;


import com.cisdi.info.simple.entity.regist.MemberRegist;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
public class MemberRegistEditDto{

    private MemberRegist memberRegist;




    public  MemberRegist getMemberRegist()
    {
        return this.memberRegist;
    }
    public  void setMemberRegist(MemberRegist memberRegist)
    {
        this.memberRegist = memberRegist;
    }

}
