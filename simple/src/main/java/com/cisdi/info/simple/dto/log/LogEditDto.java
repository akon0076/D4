package com.cisdi.info.simple.dto.log;


import com.cisdi.info.simple.entity.log.Log;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
import com.cisdi.info.simple.entity.permission.Operator;
public class LogEditDto{

    private Log log;


    //外键实体是：Operator
    private List<Operator> operatorOperators;


    public  Log getLog()
    {
        return this.log;
    }
    public  void setLog(Log log)
    {
        this.log = log;
    }

    public List<Operator> getOperatorOperators()
    {
        return this.operatorOperators;
    }
    public void setOperatorOperators(List<Operator> operatorOperators)
    {
        this.operatorOperators = operatorOperators;
    }
}
