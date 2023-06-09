package top.cadecode.uniboot.common.core.util;

import cn.hutool.core.util.ArrayUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import top.cadecode.uniboot.common.core.exception.UtilException;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;

/**
 * Spring IOC 容器工具
 *
 * @author Cade Li
 * @since 2023/6/9
 */
@Component
public class SpringUtil implements BeanFactoryPostProcessor, ApplicationContextAware {

    /**
     * 使用 @PostConstruct 时，ApplicationContext 还未加载，导致空指针
     * 注入 ConfigurableListableBeanFactory 操作 bean
     */
    private static ConfigurableListableBeanFactory BEAN_FACTORY;
    private static ApplicationContext APPLICATION_CONTEXT;

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static ListableBeanFactory getBeanFactory() {
        return null == BEAN_FACTORY ? APPLICATION_CONTEXT : BEAN_FACTORY;
    }

    public static ConfigurableListableBeanFactory getConfigurableBeanFactory() {
        final ConfigurableListableBeanFactory factory;
        if (null != BEAN_FACTORY) {
            factory = BEAN_FACTORY;
        } else if (APPLICATION_CONTEXT instanceof ConfigurableApplicationContext) {
            factory = ((ConfigurableApplicationContext) APPLICATION_CONTEXT).getBeanFactory();
        } else {
            throw new UtilException("no listable bean factory");
        }
        return factory;
    }

    /**
     * 通过name获取 Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) getBeanFactory().getBean(name);
    }

    /**
     * 通过class获取Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getBeanFactory().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getBeanFactory().getBean(name, clazz);
    }

    /**
     * 通过类型参考返回带泛型参数的Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(TypeReference<T> reference) {
        final ParameterizedType parameterizedType = (ParameterizedType) reference.getType();
        final Class<T> rawType = (Class<T>) parameterizedType.getRawType();
        final Class<?>[] genericTypes = Arrays.stream(parameterizedType.getActualTypeArguments()).map(type -> (Class<?>) type).toArray(Class[]::new);
        final String[] beanNames = getBeanFactory().getBeanNamesForType(ResolvableType.forClassWithGenerics(rawType, genericTypes));
        return getBean(beanNames[0], rawType);
    }

    /**
     * 获取指定类型对应的所有Bean，包括子类
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    /**
     * 获取指定类型对应的Bean名称，包括子类
     */
    public static String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    /**
     * 获取配置文件配置项的值
     */
    public static String getProperty(String key) {
        if (null == APPLICATION_CONTEXT) {
            return null;
        }
        return APPLICATION_CONTEXT.getEnvironment().getProperty(key);
    }

    /**
     * 获取应用程序名称
     */
    public static String getApplicationName() {
        return getProperty("spring.application.name");
    }

    /**
     * 获取当前的环境配置，无配置返回null
     */
    public static String[] getActiveProfiles() {
        if (null == APPLICATION_CONTEXT) {
            return null;
        }
        return APPLICATION_CONTEXT.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return ArrayUtil.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
    }

    /**
     * 动态向Spring注册Bean
     */
    public static <T> void registerBean(String beanName, T bean) {
        final ConfigurableListableBeanFactory factory = getConfigurableBeanFactory();
        factory.autowireBean(bean);
        factory.registerSingleton(beanName, bean);
    }

    /**
     * 注销 bean
     */
    public static void unregisterBean(String beanName) {
        final ConfigurableListableBeanFactory factory = getConfigurableBeanFactory();
        if (factory instanceof DefaultSingletonBeanRegistry) {
            DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) factory;
            registry.destroySingleton(beanName);
        } else {
            throw new UtilException("can not unregister bean");
        }
    }

    /**
     * 发布事件
     */
    public static void publishEvent(Object event) {
        if (null != APPLICATION_CONTEXT) {
            APPLICATION_CONTEXT.publishEvent(event);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BEAN_FACTORY = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        APPLICATION_CONTEXT = applicationContext;
    }
}
