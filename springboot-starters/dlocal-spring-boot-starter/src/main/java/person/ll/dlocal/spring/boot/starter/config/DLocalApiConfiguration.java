package person.ll.dlocal.spring.boot.starter.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author wll
 * Created Date:  2019/10/9
 * Description:   {dokypay 接口}
 */
@ConfigurationProperties(
        prefix = "dlocal.api"
)
@Data
@Accessors(chain = true)
public class DLocalApiConfiguration {
    //创建付款,创建预授权，捕获授权
    private String payments;
    //查找付款方式
    private String paymentsMethods;
    //退款
    private String refunds;


}
