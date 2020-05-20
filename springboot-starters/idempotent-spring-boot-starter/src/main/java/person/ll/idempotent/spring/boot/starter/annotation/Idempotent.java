package person.ll.idempotent.spring.boot.starter.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    String[] keys() default {};

    long expire() default -1;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    String msg() default "please try it latter!";

    String delimiter() default ":";
}
