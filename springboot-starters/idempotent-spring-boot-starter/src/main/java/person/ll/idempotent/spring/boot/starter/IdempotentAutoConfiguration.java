package person.ll.idempotent.spring.boot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import person.ll.idempotent.spring.boot.starter.strategy.store.IdempotentStore;
import person.ll.idempotent.spring.boot.starter.strategy.store.redis.RedisIdempotentStore;

@Configuration
@EnableConfigurationProperties(IdempotentProperties.class)
@ComponentScan("person.ll.idempotent.spring.boot.starter")
public class IdempotentAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IdempotentStore.class)
    @ConditionalOnBean(StringRedisTemplate.class)
    public IdempotentStore idempotentStore(StringRedisTemplate stringRedisTemplate){
        return new RedisIdempotentStore(stringRedisTemplate);
    }

}
