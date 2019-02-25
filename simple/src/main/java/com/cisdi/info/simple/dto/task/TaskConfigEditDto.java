package com.cisdi.info.simple.dto.task;


import com.cisdi.info.simple.entity.task.TaskConfig;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
public class TaskConfigEditDto{

    private TaskConfig taskConfig;




    public  TaskConfig getTaskConfig()
    {
        return this.taskConfig;
    }
    public  void setTaskConfig(TaskConfig taskConfig)
    {
        this.taskConfig = taskConfig;
    }

}
