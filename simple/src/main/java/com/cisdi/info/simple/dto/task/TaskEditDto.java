package com.cisdi.info.simple.dto.task;


import com.cisdi.info.simple.entity.task.Task;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
public class TaskEditDto{

    private Task task;




    public  Task getTask()
    {
        return this.task;
    }
    public  void setTask(Task task)
    {
        this.task = task;
    }

}
