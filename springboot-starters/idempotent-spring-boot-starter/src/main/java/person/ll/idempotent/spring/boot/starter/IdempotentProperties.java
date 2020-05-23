package person.ll.idempotent.spring.boot.starter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

@ConfigurationProperties(
        prefix = "idempotent"
)
public class IdempotentProperties {
    boolean enable;
    @Getter
    @Setter
    int order = Ordered.HIGHEST_PRECEDENCE+1;
}
