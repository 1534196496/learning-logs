package person.ll.dokypay.spring.boot.starter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import person.ll.dokypay.spring.boot.starter.config.DokypayApiConfiguration;
import person.ll.dokypay.spring.boot.starter.config.DokypayConfiguration;
import person.ll.dokypay.spring.boot.starter.service.DokypayService;

/**
 *
 * @author wll
 * Description:   {类描述}
 */
@Configuration
@EnableConfigurationProperties({DokypayConfiguration.class, DokypayApiConfiguration.class})
public class DokypayAutoConfiguration {
    @Bean
    public DokypayService dokypayService(DokypayConfiguration dokypayConfiguration, DokypayApiConfiguration dokypayApiConfiguration){
        return new DokypayService(dokypayConfiguration,dokypayApiConfiguration);
    }
}
