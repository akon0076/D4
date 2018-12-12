package com.cisdi.info.simple.dto.system;


import com.cisdi.info.simple.entity.system.CodeTable;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
import com.cisdi.info.simple.entity.system.CodeTable;
public class CodeTableEditDto{

private CodeTable codeTable;


//外键实体是：CodeTable
private List<CodeTable> parentCodeTables;


public  CodeTable getCodeTable()
{
    return this.codeTable;
}
public  void setCodeTable(CodeTable codeTable)
{
    this.codeTable = codeTable;
}

public List<CodeTable> getParentCodeTables()
{
    return this.parentCodeTables;
}
public void setParentCodeTables(List<CodeTable> parentCodeTables)
{
    this.parentCodeTables = parentCodeTables;
}
}
