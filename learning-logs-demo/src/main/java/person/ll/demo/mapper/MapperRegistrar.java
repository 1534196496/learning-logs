package person.ll.demo.mapper;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.*;

public class MapperRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {

        //
        Set<String> packagesToScan = getPackage(importingClassMetadata);


        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry,false){
            @SneakyThrows
            @Override
            protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
                Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
                for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
                    GenericBeanDefinition beanDefinition = (GenericBeanDefinition)beanDefinitionHolder.getBeanDefinition();
                    beanDefinition.getPropertyValues().add("clazz",Class.forName(beanDefinition.getBeanClassName()));
                    beanDefinition.setBeanClass(MapperProxyFactoryBean.class);
                }

                return beanDefinitionHolders;
            }

            @Override
            public Set<BeanDefinition> findCandidateComponents(String basePackage) {
                Set<BeanDefinition> candidateComponents = super.findCandidateComponents(basePackage);
                return candidateComponents;
            }

            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
               return beanDefinition.getMetadata().hasAnnotation(Mapper.class.getName());
            }
        };
       scanner.addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
        scanner.setBeanNameGenerator(new DefaultBeanNameGenerator());
        scanner.scan(packagesToScan.toArray(new String[0]));

    }

    private Set<String> getPackage(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName()));
        String[] basePackages = attributes.getStringArray("basePackages");
        Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
        Set<String> packagesToScan = new LinkedHashSet<>(Arrays.asList(basePackages));
        for (Class<?> basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
        }
        if (packagesToScan.isEmpty()) {
            packagesToScan.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return packagesToScan;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
