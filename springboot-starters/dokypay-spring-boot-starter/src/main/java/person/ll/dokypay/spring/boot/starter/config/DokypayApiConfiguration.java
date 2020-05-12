package person.ll.dokypay.spring.boot.starter.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wll
 * Description:   {dokypay 接口配置}
 */
@ConfigurationProperties(
        prefix = "dokypay.api"
)
@Data
@Accessors(chain = true)
public class DokypayApiConfiguration {
    //API方式支付接口
    private String unifiedorder;
    //API方式订单查询接口
    private String tradeQuery;
    //API方式退款接口
    private String refund;
    //API方式退款查询接口
    private String refundQuery;
    //API方式代付接口
    private String payout;
    //API账户查询接口
    private String accountQuery;
    //API印尼账户风险校验接口
    private String bankAccountRisk;
    //API方式订阅创建接口
    private String subscribeCreate;
    //API方式订阅续订接口
    private String subscribeRenew;
    //API方式订阅退订接口
    private String unsubscribe;
}
