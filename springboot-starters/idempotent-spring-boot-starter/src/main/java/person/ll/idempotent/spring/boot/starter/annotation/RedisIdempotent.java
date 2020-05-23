//package person.ll.idempotent.spring.boot.starter.annotation;
//
//import org.springframework.core.annotation.AliasFor;
//import person.ll.idempotent.spring.boot.starter.constant.Constants;
//
//import java.lang.annotation.*;
//import java.util.concurrent.TimeUnit;
//
//@Target({ElementType.METHOD})
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
//@Idempotent()
//public @interface RedisIdempotent {
//    @AliasFor(annotation = Idempotent.class)
//    String[] keys() default {};
//    @AliasFor(annotation = Idempotent.class)
//    long leaseTime() default 30000L;
//    @AliasFor(annotation = Idempotent.class)
//    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
//    @AliasFor(annotation = Idempotent.class)
//    String msg() default Constants.DEFAULT_MSG;
//    @AliasFor(annotation = Idempotent.class)
//    String delimiter() default Constants.COLON;
//}
