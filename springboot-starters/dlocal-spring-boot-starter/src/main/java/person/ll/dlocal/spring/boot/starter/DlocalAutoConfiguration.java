package person.ll.dlocal.spring.boot.starter;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import person.ll.dlocal.spring.boot.starter.config.DLocalApiConfiguration;
import person.ll.dlocal.spring.boot.starter.config.DLocalConfiguration;
import person.ll.dlocal.spring.boot.starter.service.DLocalService;

/**
 *
 * @author wll
 * Created Date:  2019/10/8
 * Description:   {自动配置类}
 */
@Configuration
@EnableConfigurationProperties({DLocalApiConfiguration.class, DLocalConfiguration.class})
public class DlocalAutoConfiguration {

    @Bean
    public DLocalService dLocalService(DLocalApiConfiguration dLocalApiConfiguration, DLocalConfiguration dLocalConfiguration){
        return new DLocalService(dLocalApiConfiguration,dLocalConfiguration);
    }

}
