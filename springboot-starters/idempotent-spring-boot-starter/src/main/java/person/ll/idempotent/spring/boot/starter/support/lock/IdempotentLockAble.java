package person.ll.idempotent.spring.boot.starter.support.lock;

import java.util.concurrent.TimeUnit;

public interface IdempotentLockAble{

    <V> Boolean lock(IdempotentLockInfo<V> lock, long waitTime, long leaseTime, TimeUnit timeUnit)throws InterruptedException;

    <V>Boolean unlock(IdempotentLockInfo<V> lock);


}
