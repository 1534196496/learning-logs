package person.ll.idempotent.spring.boot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "idempotent"
)
public class IdempotentProperties {
}
