package top.cadecode.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("top.cadecode.**.mapper")
@ComponentScan("top.cadecode")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
