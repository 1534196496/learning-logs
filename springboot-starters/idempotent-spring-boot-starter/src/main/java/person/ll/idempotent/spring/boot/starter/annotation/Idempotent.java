package person.ll.idempotent.spring.boot.starter.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    String key() default "";

    long expire() default -1;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    String msg() default "please try it latter!";
}
