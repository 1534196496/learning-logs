package person.ll.dlocal.spring.boot.starter.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author wll
 * Description:   {dlocal 支付回调}
 */
@Data
@Accessors(chain = true)
public class PayNotifyEntity {
    private String id;
    private BigDecimal amount;
    private String status;
    private String statusDetail;
    private String statusCode;
    private String currency;
    private String country;
    private String paymentMethodId;
    private String paymentMethodType;
    private String paymentMethodFlow;
    private Payer payer;
    private String orderId;
    private String notificationUrl;
    private String createdDate;
}
