package person.ll.dokypay.spring.boot.starter.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import person.ll.dokypay.spring.boot.starter.enums.OrderStatusEnum;

/**
 *
 * @author wll
 * Created Date:  2019/10/9
 * Description:   {类描述}
 */
@Data
@Accessors(chain = true)
public class PayNotifyEntity {
    //transNo
    //交易流水ID	Dokypay平台交易流水号( 同支付接口返回的tradeNo )
    private String transNo;
    //merTransNo
    //商户订单号	商户生成的订单号
    private String merTransNo;
    //currency
    //币种	源订单的币种
    private String currency;
    //amount
    //金额	源订单金额
    private String amount;
    //processCurrency
    //处理币种	用户实际支付时使用的币种
    private String processCurrency;
    //processAmount
    //处理金额	用户实际支付金额
    private String processAmount;
    //transStatus
    //订单状态	详见订单状态
    private OrderStatusEnum transStatus;
    //createTime
    //创建时间	订单创建时间（GMT+8 时间）
    private String createTime;
    //updateTime
    //最后处理时间	最后处理时间 （GMT+8 时间）
    private String updateTime;
    //payerName
    //付款人姓名	如果Dokypay能确定此参数时则返回
    private String payerName;
    //payerEmail
    //付款人邮箱	如果Dokypay能确定此参数时则返回
    private String payerEmail;
    //payerMobile
    //付款人电话	如果Dokypay能确定此参数时则返回
    private String payerMobile;
    //sign
    //签名	上述信息均参与签名，详见签名机
    private String sign;

    private String pmId;
    private String paymentType;
    private String paymentChannel;
}
