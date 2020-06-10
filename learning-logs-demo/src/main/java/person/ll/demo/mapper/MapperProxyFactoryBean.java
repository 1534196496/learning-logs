package person.ll.demo.mapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MapperProxyFactoryBean<T> implements FactoryBean<T>, EmbeddedValueResolverAware {

    private StringValueResolver embeddedValueResolver;

    @Setter
    private Class<?> clazz;

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(), new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Select declaredAnnotation = method.getDeclaredAnnotation(Select.class);
                        String format = MessageFormat.format(declaredAnnotation.value(), args);
                        System.out.println("执行SQL:"+format);
                        return null;
                    }
                });


    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.embeddedValueResolver = resolver;
    }
}
