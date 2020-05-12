package person.ll.dokypay.spring.boot.starter.enums;

/**
 *
 * @author wll
 */
public enum  OrderStatusEnum {
    /**
     *
     */
    pending("待支付"),
    processing("支付中"),
    success("支付成功"),
    failure("支付失败"),
    timeout("支付超时"),
    cancel("已取消")
    ;
    private String desc;
    OrderStatusEnum(String desc){
        this.desc = desc;
    }
    String getDesc(){
        return desc;
    }
}
