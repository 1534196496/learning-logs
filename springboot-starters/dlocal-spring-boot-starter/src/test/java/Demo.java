import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson.JSONObject;
import person.ll.dlocal.spring.boot.starter.config.DLocalApiConfiguration;
import person.ll.dlocal.spring.boot.starter.config.DLocalConfiguration;
import person.ll.dlocal.spring.boot.starter.service.DLocalService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Demo {


    public static void main(String[] args)throws Exception {
        DLocalService dLocalService = init();
//        testGet(dLocalService);
        String response = testPay(dLocalService);
//        JSONObject jsonObject = JSONObject.parseObject(response);
//        testRefund(dLocalService,jsonObject.getString("id"),new BigDecimal("100"));
//
    }


    private static DLocalService init() {
        DLocalApiConfiguration dLocalApiConfiguration = new DLocalApiConfiguration()
                .setPayments("https://sandbox.dlocal.com/payments")
                .setPaymentsMethods(" https://sandbox.dlocal.com/payments-methods")
                .setRefunds("https://sandbox.dlocal.com/refunds")
                ;
        DLocalConfiguration dLocalConfiguration = new DLocalConfiguration()
                .setXLogin("8ecd958251")
                .setSecretKey("9d9469e83e4561c12f5ea259c3f6f3871")
                .setXTransKey("222fe11da6")
                .setCallbackUrl("https://www.google.com")
                .setNotificationUrl("https://www.google.com")

                ;
        return new DLocalService(dLocalApiConfiguration,dLocalConfiguration);
    }


    private static void testRefund(DLocalService dLocalService, String payment_id, BigDecimal amount) {
        JSONObject request = new JSONObject();
        request.put("payment_id",payment_id);
        request.put("notification_url",dLocalService.getDLocalConfiguration().getRefundNotificationUrl());
        request.put("amount",amount);
        request.put("currency","INR");
        System.out.println(dLocalService.post(dLocalService.getDLocalApiConfiguration().getRefunds(), request.toJSONString()));
    }


    private static String testPay(DLocalService dLocalService) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount",100);
        jsonObject.put("country","BR");
        jsonObject.put("payment_method_id","CARD");
        jsonObject.put("payment_method_flow","DIRECT");
        jsonObject.put("currency","BRL");
        JSONObject payer = new JSONObject();
        payer.put("name","123");
        payer.put("email","456@example.com");
        payer.put("document","73438146720");
        JSONObject address = new JSONObject();
        address.put("city","1");
        address.put("street","2");
        address.put("number","3");
        payer.put("address",address);
        jsonObject.put("payer",payer);
        JSONObject card = new JSONObject();
        card.put("token", "CV-4418c38e-b83a-484a-b330-742911f97b88");
        card.put("capture",false);
        jsonObject.put("card", card);
        String payorderNo = "bigplayerseeee8358-d521-4936-9ef8-d37633bfed51";
        jsonObject.put("order_id", payorderNo);
        System.out.println(jsonObject.getString("order_id"));
        jsonObject.put("notification_url","http://ecb5525d.ngrok.io/callback/dlocal/pay");
        StrBuilder sb = new StrBuilder()
                .append(dLocalService.getDLocalConfiguration().getCallbackUrl())
                .append("?merTransNo=")
                .append(payorderNo);
        jsonObject.put("callback_url",sb.toString());
        String response = dLocalService.post(dLocalService.getDLocalApiConfiguration().getPayments(), jsonObject.toJSONString());
        System.out.println(response);
        return response;
    }

    private static void testGet(DLocalService dLocalService) {
        Map<String,Object> query = new HashMap<>();
        query.put("country","AR");
        System.out.println(dLocalService.get(dLocalService.getDLocalApiConfiguration().getPaymentsMethods(), query));
    }
}
