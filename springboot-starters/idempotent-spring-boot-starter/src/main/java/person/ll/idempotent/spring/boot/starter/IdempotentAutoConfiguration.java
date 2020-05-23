package person.ll.idempotent.spring.boot.starter;

import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import person.ll.idempotent.spring.boot.starter.support.lock.redis.RedisIdempotentLock;
import person.ll.idempotent.spring.boot.starter.support.lock.redission.RedissonIdempotentLock;

@Configuration

@EnableConfigurationProperties(IdempotentProperties.class)
@ComponentScan("person.ll.idempotent.spring.boot.starter")
@ConditionalOnProperty(prefix = "idempotent",value = "enable",havingValue = "true")
public class IdempotentAutoConfiguration {




    @Configuration
    @ConditionalOnClass(StringRedisTemplate.class)
    @ConditionalOnBean(StringRedisTemplate.class)
    protected static class RedisIdempotentStoreConfiguration{
        @Bean
        protected RedisIdempotentLock redisIdempotentLock(StringRedisTemplate stringRedisTemplate){
            return new RedisIdempotentLock(stringRedisTemplate);
        }
    }


    @Configuration
    @ConditionalOnClass(RedissonClient.class)
    @ConditionalOnBean(RedissonClient.class)
    protected static class RedissionIdempotentStoreConfiguration{
        @Bean
        protected RedissonIdempotentLock redissonIdempotentLock(RedissonClient redissonClient){
            return new RedissonIdempotentLock(redissonClient);
        }
    }



}
