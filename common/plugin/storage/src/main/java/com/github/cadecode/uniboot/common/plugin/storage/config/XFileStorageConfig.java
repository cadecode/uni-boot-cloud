package com.github.cadecode.uniboot.common.plugin.storage.config;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.plugin.storage.handler.AbstractStorageHandler;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Optional;

/**
 * x-file-storage 配置类
 *
 * @author Cade Li
 * @since 2023/10/25
 */
@EnableFileStorage
@RequiredArgsConstructor
@Configuration
public class XFileStorageConfig {

    public static final String BEAN_NAME_FILE_RECORDER = "fileRecorder";

    @Bean
    public FileRecorderBeanDefinitionRegistryPostProcessor fileRecorderBeanDefinitionRegistryPostProcessor() {
        return new FileRecorderBeanDefinitionRegistryPostProcessor();
    }

    /**
     * 解决自定义 AbstractStorageHandler 不能替代 x-file-storage 框架默认 FileRecorder
     */
    public static class FileRecorderBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            String[] handlerBeanNames = beanFactory.getBeanNamesForType(AbstractStorageHandler.class);
            // 查找 primary 的，不存在则取第一个
            if (ObjUtil.isNotEmpty(handlerBeanNames)) {
                Optional<BeanDefinition> primaryBF = Arrays.stream(handlerBeanNames)
                        .map(beanFactory::getBeanDefinition)
                        .filter(BeanDefinition::isPrimary)
                        .findFirst();
                BeanDefinition handlerBF;
                handlerBF = primaryBF.orElseGet(() -> beanFactory.getBeanDefinition(handlerBeanNames[0]));
                if (beanFactory instanceof DefaultListableBeanFactory) {
                    ((DefaultListableBeanFactory) beanFactory).removeBeanDefinition(BEAN_NAME_FILE_RECORDER);
                    ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(BEAN_NAME_FILE_RECORDER, handlerBF);
                }
            }
        }
    }
}
