package person.ll.idempotent.spring.boot.starter.strategy.store.redis;

import org.springframework.data.redis.core.RedisTemplate;
import person.ll.idempotent.spring.boot.starter.strategy.store.IdempotentStore;

import java.util.concurrent.TimeUnit;

public class RedisIdempotentStore implements IdempotentStore<String, String> {

    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean setNx(String key, String value, long expireTime, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
    }

    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
