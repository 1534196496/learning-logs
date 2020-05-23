package person.ll.idempotent.spring.boot.starter.function;

import java.util.List;

@FunctionalInterface
public interface ValueMapper<T> {
    T mapper(List<String> keys);
}
