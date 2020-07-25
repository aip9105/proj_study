package com.task.dynamic.config.task;

import com.task.dynamic.service.task.TaskService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 动态定时任务开启
 */
@Configuration
@EnableScheduling
@Slf4j
public class DynamicTask implements SchedulingConfigurer, DisposableBean {
    private static final String TASK_ID_PREFIX = "dynamic-task-";
    private volatile ScheduledTaskRegistrar registrar;
    private final ConcurrentHashMap<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CronTask> cronTasks = new ConcurrentHashMap<>();

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        this.registrar = registrar;
    }

    public void addTasks(List<Task> tasks) {
        this.refreshTasks(tasks);
    }

    public void removeTask(String taskId) {
        taskId = TASK_ID_PREFIX + taskId;
        if (this.scheduledFutures.containsKey(taskId)) {
            this.scheduledFutures.get(taskId).cancel(false);
            this.scheduledFutures.remove(taskId);
            this.cronTasks.remove(taskId);
        }
    }

    private void refreshTasks(List<Task> tasks) {
        tasks.forEach(task -> {
            String expression = task.getCron();
            if (StringUtils.isEmpty(expression) || !CronSequenceGenerator.isValidExpression(expression)) {
                log.error("动态定时任务 cron 表达式不合法：{}", expression);
                return;
            }
            // 如果配置一致，则不需要重新创建定时任务
            if (this.scheduledFutures.containsKey(task.getTaskId())
                && this.cronTasks.get(task.getTaskId()).getExpression().equals(expression)) {
                log.info("任务无需更改，任务 ID：{}", task.getTaskId());
                return;
            }
            // 如果策略执行时间发生了变化，则取消当前策略的任务
            if (this.scheduledFutures.containsKey(task.getTaskId())) {
                this.removeTask(task.getTaskId());
            }
            CronTask cronTask = new CronTask(task.getTaskService(), expression);
            ScheduledFuture<?> future = this.registrar.getScheduler().schedule(cronTask.getRunnable(), cronTask.getTrigger());
            this.cronTasks.put(task.getTaskId(), cronTask);
            this.scheduledFutures.put(task.getTaskId(), future);
        });
    }

    @Override
    public void destroy() {
        this.registrar.destroy();
    }

    @Data
    public static class Task {
        private String cron;
        private String taskId;
        private TaskService taskService;

        public void setTaskId(String taskId) {
            this.taskId = TASK_ID_PREFIX + taskId;
        }
    }
}
