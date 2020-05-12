package person.ll.idempotent.spring.boot.starter.strategy.store;

import java.util.concurrent.TimeUnit;

public interface IdempotentStore<K,V> {

    V get(K key);

    Boolean setNx(K key, V value, long expireTime, TimeUnit timeUnit);

    Boolean delete(K key);
}
