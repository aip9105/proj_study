package com.task.dynamic.service.task;

import com.task.dynamic.config.task.DynamicTask;
import com.task.dynamic.spring.SpringContextUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * 定时任务
 */
@Data
@Slf4j
public class TaskService implements Runnable {
    private String beanName;
    private String param;
    private String taskId;

    private DynamicTask dynamicTask;
    /**
     * Spring 应用上下文环境
     */
    private static ApplicationContext applicationContext;

    @SneakyThrows
    @Override
    public void run() {
        log.info("任务开始，任务 ID：{}", this.taskId);

        log.info("BeanName：{}, 参数：{}", this.beanName, this.param);

        ITaskExecuteService taskExecuteService =SpringContextUtil.getBean(this.beanName, ITaskExecuteService.class);

        if (Objects.isNull(taskExecuteService)) {
            log.error("BeanName:{} 未找到", this.beanName);
            this.dynamicTask.removeTask(this.taskId);

            log.info("任务已销毁，任务 ID：{}", this.taskId);
            return;
        }

        taskExecuteService.execute(this.param);

        log.info("任务完成，任务 ID：{}", this.taskId);

        this.dynamicTask.removeTask(this.taskId);

        log.info("任务已销毁，任务 ID：{}", this.taskId);
    }
}
