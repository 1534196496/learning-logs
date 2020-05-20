package person.ll.idempotent.spring.boot.starter.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;
import person.ll.idempotent.spring.boot.starter.annotation.Idempotent;
import person.ll.idempotent.spring.boot.starter.exception.IdempotentException;
import person.ll.idempotent.spring.boot.starter.strategy.store.IdempotentStore;
import person.ll.idempotent.spring.boot.starter.util.MD5Util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
public class IdempotentAspect implements EmbeddedValueResolverAware {

    /**
     * 用于SpEL表达式解析.
     */
    private final SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    @Autowired
    private IdempotentStore<Object,Object> idempotentStore;


    private StringValueResolver stringValueResolver;





    @Around(value = "@annotation(idempotent)")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable{
        String key;
        if(idempotent.keys().length == 0){
            key = getDefaultKey(joinPoint);
        }else {
            key = Arrays.stream(idempotent.keys()).map(k -> praseSpEL(joinPoint, k)).collect(Collectors.joining(idempotent.delimiter()));
        }

        if(idempotentStore.setNx(key,key,idempotent.expire(),idempotent.timeunit())){
            try {
                return joinPoint.proceed();
            }finally {
                idempotentStore.delete(key);
            }
        }else {
            throw new IdempotentException(idempotent.msg());
        }




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

    /**
     * 实现该接口能够获取Spring EL解析器，用户的自定义注解需要支持spel表达式的时候可以使用，非常方便
     * @param resolver
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
    }
}
