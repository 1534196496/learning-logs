package person.ll.idempotent.spring.boot.starter.support.lock;

import lombok.Getter;

@Getter
public class IdempotentLockInfo<V>{
    private final String key;
    private final V value;

    private IdempotentLockInfo(String key,V value){
        this.key = key;
        this.value = value;
    }

    public static <V> IdempotentLockInfo<V> create(String key, V v){
        return new IdempotentLockInfo<>(key,v);
    }

    public static <V> IdempotentLockInfo<V> create(String key){
        return new IdempotentLockInfo<>(key,null);
    }

}
