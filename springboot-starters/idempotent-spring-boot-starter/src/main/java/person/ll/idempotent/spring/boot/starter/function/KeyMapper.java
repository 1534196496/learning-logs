package person.ll.idempotent.spring.boot.starter.function;

import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface
public interface KeyMapper{
     String mapper(List<String> keys,String delimiter);

}
