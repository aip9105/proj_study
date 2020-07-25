# 动态定时任务

1.定时任务自动运行

2.动态定时任务使用方法 

```
List<DynamicTask.Task> taskList = new ArrayList<>();
for (AddrDate addrDate : dateList) {
    //针对缺考定时
    DynamicTask.Task task = new DynamicTask.Task();
    TaskService taskService = new TaskService();
    taskService.setBeanName("autoMarkLackExamTaskImpl");
    //开始考试时间 +30分钟 标记缺考 添加到定时任务
    long timeStamp = addrDate.getExamDate().getTime()+addrDate.getBegTime().getTime()+8*3600*1000+30*60*1000;
    String cron = CronUtils.getCronByDate(new Date(timeStamp));
    task.setCron(cron);
    task.setTaskId("lack_exam_"+addrDate.getDateId());
    taskList.add(task);
    String param = String.valueOf(addrDate.getDateId());
    taskService.setParam(param);
    task.setTaskService(taskService);
    taskService.setTaskId(task.getTaskId());
    taskService.setDynamicTask(dynamicTask);
    //针对缺考定时
    taskService.setBeanName("autoSubmitPaperTaskImpl");
    timeStamp = addrDate.getExamDate().getTime()+addrDate.getEndTime().getTime()+8*3600*1000;
    cron = CronUtils.getCronByDate(new Date(timeStamp));
    task.setCron(cron);
    task.setTaskId("submit_paper_"+addrDate.getDateId());
    taskList.add(task);
    taskService.setParam(param);
    task.setTaskService(taskService);
    taskService.setTaskId(task.getTaskId());
    taskService.setDynamicTask(dynamicTask);
}
dynamicTask.addTasks(taskList);
```

```
//hellowworld定时打印
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
```

前端请求

```
POST localhost:8080/hello/openPrintHelloWorld
Content-Type: application/json
####
```

3.静态定时任务

```
SaticScheduleTask
```