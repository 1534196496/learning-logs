package person.ll.dokypay.spring.boot.starter.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import person.ll.dokypay.spring.boot.starter.enums.OrderStatusEnum;

/**
 *
 * @author wll
 * Created Date:  2019/10/12
 * Description:   {类描述}
 */
@Data
@Accessors(chain = true)
public class RefundNotifyEntity {
    //resultCode
    //统一下单码	当统一下单码为0000时，表明下单成功,具体业务码，请参照统一下单码表
    private String resultCode;
    //message
    //下单码描述	当下单码描述为success时，表明下单成功,具体业务码，请参照统一下单码表
    //当下单码描述为success,或统一下单码为0000时，才会出现以下内容
    private String message;
    //amount
    //支付金额	支付金额
    private String amount;
    //currency
    //币种	币种
    private String currency;
    //processCurrency
    //交易处理币种	用户实际支付时使用的币种
    private String processCurrency;
    //refundNo
    //退款订单号	Dokypay生成的退款订单号
    private String refundNo;
    //processAmount
    //交易处理金额	用户实际支付金额
    private String processAmount;
    //status
    //退款状态	交易状态，与支付接口通知机制返回订单状态相同，详见订单状态
    private OrderStatusEnum status;
    //createTime
    //订单创建时间	YYYY-MM-dd HH:mm:ss
    private String createTime;
    //updateTime
    //最后处理时间	YYYY-MM-dd HH:mm:ss
    private String updateTime;
    //sign
    //签名值	请参考签名机制；签名业务字段只取data中的字段数据作为业务参数
    private String sign;


}
