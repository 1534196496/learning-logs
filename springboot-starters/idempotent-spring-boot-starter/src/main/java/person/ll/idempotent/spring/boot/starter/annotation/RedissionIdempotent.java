//package person.ll.idempotent.spring.boot.starter.annotation;
//
//import org.springframework.core.annotation.AliasFor;
//import person.ll.idempotent.spring.boot.starter.constant.Constants;
//import person.ll.idempotent.spring.boot.starter.enums.LockWay;
//
//import java.lang.annotation.*;
//import java.util.concurrent.TimeUnit;
//
//@Target({ElementType.METHOD})
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
//@Idempotent
//public @interface RedissionIdempotent {
//
//    @AliasFor(annotation = Idempotent.class)
//    String[] keys() default {};
//
//    @AliasFor(annotation = Idempotent.class)
//    long waitTime() default Constants.NEVER_WAIT;
//
//    @AliasFor(annotation = Idempotent.class)
//    long leaseTime() default Constants.DEFAULT_LEASS_TIME;
//
//    @AliasFor(annotation = Idempotent.class)
//    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
//
//    @AliasFor(annotation = Idempotent.class)
//    String msg() default Constants.DEFAULT_MSG;
//
//    @AliasFor(annotation = Idempotent.class)
//    String delimiter() default Constants.COLON;
//
//    @AliasFor(annotation = Idempotent.class)
//    LockWay lockWay() default LockWay.REDISSION;
//
//}
