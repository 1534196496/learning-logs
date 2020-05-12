package person.ll.dokypay.spring.boot.starter.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author wll
 * Created Date:  2019/10/8
 * Description:   {类描述}
 */
@Data
public class DokypayResponse implements Serializable {
    //状态码	http状态码，此字段值只表明接口请求情况，不能说明业务参数无异常
    private Integer code;
    //状态描述	接口调用结果描述
    private String msg;
    //当前时间串	当前的时间串
    private Long timestamp;
    //业务返回值	描述业务调用情况的返回值，具体请参照业务接口列表 中每个接口的data值
    private JSONObject data;


}
