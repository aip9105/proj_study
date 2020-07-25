package com.task.dynamic.service.master;

import com.task.dynamic.config.task.DynamicTask;
import com.task.dynamic.service.task.TaskService;
import com.task.dynamic.util.CronUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HelloWorldServceImpl implements HelloWorldSerivce{

    private final DynamicTask dynamicTask;

    public HelloWorldServceImpl(DynamicTask dynamicTask) {
        this.dynamicTask = dynamicTask;
    }

    @Override
    public void openPrintHelloWorld() {
        List<DynamicTask.Task> taskList = new ArrayList<>();
        for (int i= 1;i<=10;i++) {
            //针对缺考定时
            DynamicTask.Task task = new DynamicTask.Task();
            TaskService taskService = new TaskService();
            taskService.setBeanName("autoPrintHelloWorldImpl");
            //当前时间1分钟后打印
            long timeStamp = System.currentTimeMillis()+1*60*1000;
            System.out.println(LocalDateTime.now());
            String cron = CronUtils.getCronByDate(new Date(timeStamp));
            task.setCron(cron);
            task.setTaskId("print"+i);
            taskList.add(task);
            String param = String.valueOf(i);
            taskService.setParam(param);
            task.setTaskService(taskService);
            taskService.setTaskId(task.getTaskId());
            taskService.setDynamicTask(dynamicTask);
        }
        dynamicTask.addTasks(taskList);
    }
}
