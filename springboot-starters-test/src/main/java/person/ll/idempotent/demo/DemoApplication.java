package person.ll.idempotent.demo;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import person.ll.idempotent.spring.boot.starter.annotation.Idempotent;
import person.ll.idempotent.spring.boot.starter.enums.LockWay;

@SpringBootApplication
@RestController
public class DemoApplication implements ApplicationContextAware {

    public ApplicationContext applicationContext;
    public static final String DEFAULT_PREFIX= "prefix";
    public static final String REDIS_PREFIX= "redis:prefix";
    public static final String REDISSION_PREFIX= "redission:prefix";
    public static void main(String[] args) {
        SpringApplication.run(new Class[]{DemoApplication.class, ConfigurableApplicationContext.class}, args);
    }

    @GetMapping("/get")
    @Idempotent(
            keys = {"'"+DEFAULT_PREFIX+"'", "#model.getP1()", "#model.p2", "'suffix'"},
            waitTime = 0L,
            leaseTime = 5000L,
            msg = "go away",
            lockWay = LockWay.CUSTOMIZE
    )
    public String get(Model model)throws Exception{
        Thread.sleep(10000);
        return "get";
    }
    @Idempotent(
            keys = {"  '"+REDIS_PREFIX+"' + #model.getP1() +  #model.p2  +  'suffix' "},
            lockWay = LockWay.REDIS
    )
    @GetMapping("/get2")
    public String get2(Model model)throws Exception{
        Thread.sleep(10000);
        return "get2";
    }


    @Idempotent(
            keys = {"  '"+REDISSION_PREFIX+"' + #model.getP1() +  #model.p2  +  'suffix' "},
            lockWay = LockWay.REDISSION
    )
    @GetMapping("/get3")
    public String get3(Model model)throws Exception{
        Thread.sleep(10000);
        return "get3";
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
