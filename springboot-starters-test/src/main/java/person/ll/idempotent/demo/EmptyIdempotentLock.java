package person.ll.idempotent.demo;

import org.springframework.stereotype.Component;
import person.ll.idempotent.spring.boot.starter.enums.LockWay;
import person.ll.idempotent.spring.boot.starter.support.lock.IdempotentLockInfo;
import person.ll.idempotent.spring.boot.starter.support.lock.IdempotentLockSupport;

import java.util.concurrent.TimeUnit;

@Component
public class EmptyIdempotentLock extends IdempotentLockSupport {

    public EmptyIdempotentLock() {
        super(LockWay.MYSQL);
    }


    @Override
    public <V> Boolean lock(IdempotentLockInfo<V> lock, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        System.out.println("Empty lock");
        return true;
    }

    @Override
    public <V> Boolean unlock(IdempotentLockInfo<V> lock) {
        System.out.println("Empty unlock");
        return true;
    }
}
