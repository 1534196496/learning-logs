package person.ll.idempotent.spring.boot.starter.support.lock.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import person.ll.idempotent.spring.boot.starter.enums.LockWay;
import person.ll.idempotent.spring.boot.starter.support.lock.IdempotentLockInfo;
import person.ll.idempotent.spring.boot.starter.support.lock.IdempotentLockSupport;

import java.util.concurrent.TimeUnit;

public class RedisIdempotentLock extends IdempotentLockSupport {


    private final StringRedisTemplate redisTemplate;

    public RedisIdempotentLock(StringRedisTemplate redisTemplate) {
        super(LockWay.REDIS);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <V> Boolean lock(IdempotentLockInfo<V> lock, long waitTime, long leaseTime, TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(lock.getKey(), (String) lock.getValue(), leaseTime, timeUnit);
    }

    @Override
    public <V> Boolean unlock(IdempotentLockInfo<V> lock) {
        return redisTemplate.delete(lock.getKey());
    }
}
