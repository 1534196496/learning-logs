package person.ll.idempotent.spring.boot.starter.support.lock.redission;

import org.redisson.api.RedissonClient;
import person.ll.idempotent.spring.boot.starter.enums.LockWay;
import person.ll.idempotent.spring.boot.starter.support.lock.IdempotentLockInfo;
import person.ll.idempotent.spring.boot.starter.support.lock.IdempotentLockSupport;

import java.util.concurrent.TimeUnit;

public class RedissonIdempotentLock extends IdempotentLockSupport {

    private final RedissonClient redissonClient;
    public RedissonIdempotentLock(RedissonClient redissonClient) {
        super(LockWay.REDISSION);
        this.redissonClient = redissonClient;
    }


    @Override
    public <V> Boolean lock(IdempotentLockInfo<V> lock, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException{
        return redissonClient.getLock(lock.getKey()).tryLock(waitTime,leaseTime,timeUnit);
    }

    @Override
    public <V> Boolean unlock(IdempotentLockInfo<V> lock) {
        redissonClient.getLock(lock.getKey()).unlock();
        return true;
    }
}
