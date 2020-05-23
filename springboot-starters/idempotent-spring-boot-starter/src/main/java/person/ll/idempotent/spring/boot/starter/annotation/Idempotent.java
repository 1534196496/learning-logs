package person.ll.idempotent.spring.boot.starter.annotation;

import person.ll.idempotent.spring.boot.starter.constant.Constants;
import person.ll.idempotent.spring.boot.starter.enums.LockWay;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    /**
     * 幂等keys
     * key = key1+delimiter+key2
     * @return
     */
    String[] keys() default {};

    /**
     *  the maximum time to acquire the lock
     */
    long waitTime() default Constants.NEVER_WAIT;

    /**
     * lease time
     * @return
     */
    long leaseTime() default Constants.DEFAULT_LEASS_TIME;

    //the maximum time to acquire the lock
    /**
     * 时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     *
     * @return
     */
    String msg() default Constants.DEFAULT_MSG;

    /**
     * 多个key的分隔符号    多个key 生成一个 幂等key
     * 如  key = key1+delimiter+key2
     * @return
     */
    String delimiter() default Constants.COLON;

    /**
     * 加锁方式
     * @return
     */
    LockWay lockWay() default LockWay.REDIS;
}
