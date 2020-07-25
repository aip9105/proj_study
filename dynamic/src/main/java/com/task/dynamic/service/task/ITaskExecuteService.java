package com.task.dynamic.service.task;

/**
 * 动态定时任务执行service
 */
public interface ITaskExecuteService {
    /**
     * 执行
     *
     * @param param 参数
     */
    void execute(String param);
}
