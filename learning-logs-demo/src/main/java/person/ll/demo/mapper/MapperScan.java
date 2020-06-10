package person.ll.demo.mapper;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MapperRegistrar.class)
public @interface MapperScan {
    String[] basePackages() default {};
    Class<?>[] basePackageClasses() default {};
}
