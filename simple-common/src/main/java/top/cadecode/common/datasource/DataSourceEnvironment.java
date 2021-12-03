package top.cadecode.common.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author Li Jun
 * @date 2021/12/3
 * @description 将数据源配置文件加入到 Environment
 */
public class DataSourceEnvironment implements EnvironmentPostProcessor {

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource path = new ClassPathResource("dataSource.yml");
        PropertySource<?> propertySource = loadDataSourceYaml(path);
        environment.getPropertySources().addLast(propertySource);
    }

    // 加载 dataSource.yml
    private PropertySource<?> loadDataSourceYaml(Resource path) {
        if (!path.exists()) {
            throw new IllegalArgumentException("数据源配置文件 " + path + " 不存在");
        }
        try {
            List<PropertySource<?>> load = this.loader.load("dataSource", path);
            if (load.size() == 0) {
                throw new IllegalArgumentException("在数据源配置文件 dataSource.yml 中没有找到任何配置");
            }
            return load.get(0);
        } catch (Exception ex) {
            throw new IllegalStateException("加载 dataSource.yml 时出错，路径是 " + path, ex);
        }
    }
}
