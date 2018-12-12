package com.cisdi.info.simple.dto.permission;


import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
public class OperatorEditDto{

private Operator operator;

//码表是：OperatorStatus
private List<CodeTable> statusCodeTables;
//码表是：OperatorType
private List<CodeTable> typeCodeTables;



public  Operator getOperator()
{
    return this.operator;
}
public  void setOperator(Operator operator)
{
    this.operator = operator;
}

public List<CodeTable> getStatusCodeTables()
{
        return this.statusCodeTables;
}
public void setStatusCodeTables(List<CodeTable> statusCodeTables)
{
    this.statusCodeTables = statusCodeTables;
}

public List<CodeTable> getTypeCodeTables()
{
        return this.typeCodeTables;
}
public void setTypeCodeTables(List<CodeTable> typeCodeTables)
{
    this.typeCodeTables = typeCodeTables;
}

}
