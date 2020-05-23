package person.ll.idempotent.spring.boot.starter.support.lock;

import person.ll.idempotent.spring.boot.starter.enums.LockWay;
import person.ll.idempotent.spring.boot.starter.exception.IdempotentException;

public abstract class IdempotentLockSupport implements IdempotentLockAble{

    private final LockWay lockWay;

    public final boolean support(LockWay lockWay){
        return this.lockWay == lockWay;
    }

    public IdempotentLockSupport(LockWay lockWay) {
        if(lockWay == null){
            throw IdempotentException.error("lockWay must not be null");
        }
        this.lockWay = lockWay;
    }
}
