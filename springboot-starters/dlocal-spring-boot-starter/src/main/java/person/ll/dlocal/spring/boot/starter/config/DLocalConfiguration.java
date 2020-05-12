package person.ll.dlocal.spring.boot.starter.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author wll
 * Created Date:  2019/10/8
 * Description:   {Dokypay 系统配置}
 */

@ConfigurationProperties(
        prefix = "dlocal"
)
@Data
@Accessors(chain = true)
public class DLocalConfiguration {
    //dLocal分配给商家的 xTransKey
    private String xTransKey;
    //dLocal分配给商家的 xLogin
    private String xLogin;
    // 商家密钥
    private String secretKey;
    //支付完成后，dLocal会将客户最终定向到此URL地址。（仅针对银行转账和现金付款）
    private String callbackUrl;
    //当付款状态产生变化时，dLocal会向此链接发送通知信息。
    private String notificationUrl;
    //退款回调  如果退款响应中的状态为Pending, 那么退款异步通知消息将会以POST的方法发送到您提供的 Notification URL 中，此通知包含以下参数：
    private String refundNotificationUrl;
}
