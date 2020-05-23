package person.ll.idempotent.spring.boot.starter.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;
import person.ll.idempotent.spring.boot.starter.annotation.Idempotent;
import person.ll.idempotent.spring.boot.starter.exception.IdempotentException;
import person.ll.idempotent.spring.boot.starter.support.lock.IdempotentLockInfo;
import person.ll.idempotent.spring.boot.starter.support.lock.IdempotentLockSupport;
import person.ll.idempotent.spring.boot.starter.util.MD5Util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
//@Order(value  = Ordered.HIGHEST_PRECEDENCE)
public class IdempotentAspect implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(IdempotentAspect.class);

    /**
     * 用于SpEL表达式解析.
     */
    private final SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    private final List<IdempotentLockSupport> locks;

    @Autowired
    public IdempotentAspect(List<IdempotentLockSupport> locks) {
        this.locks = locks;
    }


    private static final ThreadLocal<IdempotentLockInfo<String>> idempotentLockHolder = new ThreadLocal<>();

    @Around(value = "@annotation(idempotent)")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable{
        IdempotentLockInfo<String> lockInfo = createLockInfo(joinPoint, idempotent);
        for (IdempotentLockSupport lock : locks) {
            if(!lock.support(idempotent.lockWay())){
                continue;
            }

            if(lock.lock(lockInfo,idempotent.waitTime(),idempotent.leaseTime(),idempotent.timeUnit())){
                try {
                    idempotentLockHolder.set(lockInfo);
                    return joinPoint.proceed();
                }finally {
                    idempotentLockHolder.remove();
                    lock.unlock(lockInfo);
                }
            }else {
                throw new IdempotentException(idempotent.msg());
            }
        }

        throw new IdempotentException("not support lockWay!");
    }

    private <V>IdempotentLockInfo<V> createLockInfo(ProceedingJoinPoint joinPoint, Idempotent idempotent){
        String key;
        if(idempotent.keys().length == 0){
            key = getDefaultKey(joinPoint);
        }else {
            key = Arrays.stream(idempotent.keys()).map(k -> praseSpEL(joinPoint, k)).collect(Collectors.joining(idempotent.delimiter()));
        }
        IdempotentLockInfo<V> lockInfo = IdempotentLockInfo.<V>create(key,(V)key);
        return lockInfo;
    }

    private String getDefaultKey(ProceedingJoinPoint joinPoint) {
        return MD5Util.MD5(joinPoint.getSignature().toLongString());
    }


    private String praseSpEL(ProceedingJoinPoint joinPoint,String spel){

        //获取切面方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();



        //获取方法的形参名称
        String[] params = discoverer.getParameterNames(method);
        //获取方法的实际参数值
        Object[] arguments = joinPoint.getArgs();

        //设置解析spel所需的数据上下文
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0;params!=null && len < params.length; len++) {
            context.setVariable(params[len], arguments[len]);
        }
        //解析表达式并获取spel的值
        Expression expression = parser.parseExpression(spel);
        return Objects.requireNonNull(expression.getValue(context)).toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("##################  IdempotentAspect worked  ##################");
    }
}
