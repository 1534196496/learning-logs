package person.ll.idempotent.spring.boot.starter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IdempotentProperties.class)
@ComponentScan("person.ll.idempotent.spring.boot.starter")
public class IdempotentAutoConfiguration {

}
