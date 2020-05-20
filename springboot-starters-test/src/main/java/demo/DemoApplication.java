package demo;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import person.ll.idempotent.spring.boot.starter.annotation.Idempotent;

@SpringBootApplication
@RestController
public class DemoApplication implements ApplicationContextAware {

    public ApplicationContext applicationContext;
    public static final String PREFIX= "prefix";
    public static void main(String[] args) {
        SpringApplication.run(new Class[]{DemoApplication.class, ConfigurableApplicationContext.class}, args);
    }

    @GetMapping("/get")
    @Idempotent(keys = {
            "'"+PREFIX+"'",
            "#test2.getP1()",
            "#test2.p2",
            "'suffix'"

    })
//    @Idempotent
    public String get(Test test2)throws Exception{
        Thread.sleep(10000);
        return "get";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
