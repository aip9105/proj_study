package com.web.service.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
public class SpringContextUtil {
    private SpringContextUtil() {
    }

    /**
     * Spring 应用上下文环境
     */
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 根据 beanName 获取 bean
     *
     * @param name beanName
     *
     * @return bean 的实例
     */
    public static Object getBean(String name) {
        Object bean = null;
        try {
            bean = isNull(applicationContext) ? null : applicationContext.getBean(name);
        } catch (Exception e) {
            log.error("无法获取 Bean，Name{}", name);
        }
        return bean;
    }

    /**
     * 根据 beanType 获取 bean
     *
     * @param type 获取 bean 的类型
     *
     * @return bean 的实例
     */
    public static <T> T getBean(Class<T> type) {
        return isNull(applicationContext) ? null : applicationContext.getBean(type);
    }

    /**
     * 根据 beanName 和 beanType 获取 bean
     *
     * @param name beanName
     * @param type 获取 bean 的类型
     *
     * @return bean 的实例
     */
    public static <T> T getBean(String name,
                                Class<T> type) {
        return isNull(applicationContext) ? null : applicationContext.getBean(name, type);
    }

    /**
     * 根据 bean 接口获取 bean 的所有实例
     *
     * @param type 获取 bean 的接口类型
     *
     * @return key 位 beanName，value 为实例
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 此方法已经弃用，不推荐取到 applicationContext 上下文
     *
     * @return ApplicationContext applicationContext
     *
     * @deprecated (此方法已经弃用不推荐取到 applicationContext 上下文)
     */
    @Deprecated
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
