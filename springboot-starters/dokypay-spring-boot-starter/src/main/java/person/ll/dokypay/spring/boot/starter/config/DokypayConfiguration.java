package person.ll.dokypay.spring.boot.starter.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author wll
 * Description:   {Dokypay 应用配置}
 */

@ConfigurationProperties(
        prefix = "dokypay"
)
@Data
@Accessors(chain = true)
public class DokypayConfiguration {
    // 应用号
    private String appId;
    //应用秘钥
    private String appSecret;
    //商户号
    private String merchantId;
    //商户密钥
    private String merchantSecret;
//    //支付网关接入地址
//    private String paymentGateway;
    //版本号
    private String version;
    //支付同步通知地址
    private String payReturnUrl;
    //支付异步通知地址
    private String payNotifyUrl;
    // 退款异步通知地址
    private String refundNotifyUrl;

    //名词说明
    //商户号	10000
    //商户密钥	5f190aa12f6442e0be4efca58680355b
    //应用号	1001412384
    //应用秘钥	bc2d5fc0c8d2442d86c9f4fd2d4a0b6b
    //支付网关接入地址	https://gatewaybeta.dokypay.com/clientapi/unifiedorder
}
