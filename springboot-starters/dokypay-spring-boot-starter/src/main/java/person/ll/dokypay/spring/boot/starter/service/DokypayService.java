package person.ll.dokypay.spring.boot.starter.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import person.ll.dokypay.spring.boot.starter.config.DokypayApiConfiguration;
import person.ll.dokypay.spring.boot.starter.config.DokypayConfiguration;
import person.ll.dokypay.spring.boot.starter.constant.ProductConstants;
import person.ll.dokypay.spring.boot.starter.entity.DokypayResponse;
import person.ll.dokypay.spring.boot.starter.entity.PayNotifyEntity;
import person.ll.dokypay.spring.boot.starter.enums.OrderStatusEnum;
import person.ll.dokypay.spring.boot.starter.utils.DokyPaySignUtil;
import person.ll.dokypay.spring.boot.starter.utils.MapBeanUtil;

import java.util.Map;

/**
 *
 * @author wll
 * Description:   {类描述}
 */
@Slf4j
public class DokypayService {
    @Getter
    private DokypayConfiguration dokypayConfig;
    @Getter
    private DokypayApiConfiguration dokypayApiConfiguration;
    public DokypayService(DokypayConfiguration dokypayConfig,DokypayApiConfiguration dokypayApiConfiguration) {
        this.dokypayConfig = dokypayConfig;
        this.dokypayApiConfiguration = dokypayApiConfiguration;
    }

    /**
     *
     * @param json 请求参数（无需包含sign,version字段）
     * @param url 请求接口
     * @param signKey 签名key
     * @return
     */
    public DokypayResponse request(String json, String url, String signKey){
        Map map = JSONObject.parseObject(json, Map.class);
        map.put("version",dokypayConfig.getVersion());
        this.doSign(map,signKey);
        HttpResponse httpResponse = HttpRequest.post(url)
                .body(JSONObject.toJSONString(map))
                .header("Content-Type","application/json;charset=utf-8")
                .execute();
        if(httpResponse.getStatus() == HttpStatus.HTTP_OK){
            return JSONObject.parseObject(httpResponse.body(), DokypayResponse.class);
        }else {
            log.error("请求dokypay 失败:{}",httpResponse.body());
            throw new RuntimeException(String.format("请求dokypay 失败:%s",httpResponse.body()));
        }
    }

    /**
     * 验证签名
     * @param date 请求数据
     * @param sign 签名
     * @param signKey 签名key
     * @return
     */
    public  boolean verfiySign(Object date,String sign,String signKey){
        if(StringUtils.isBlank(sign)){
            log.error("签名字段为空");
            return false;
        }
        Map<String,String> map;
        try {
            map  = MapBeanUtil.object2Map(date);
        }catch (Exception e){
            log.error("验证失败:",e);
            return false;
        }
        return sign.equals(DokyPaySignUtil.doEncrypt(map,signKey));
    }

    private void doSign(Map<String,String> map,String signKey){
        map.put(DokyPaySignUtil.SIGN_FIELD,DokyPaySignUtil.doEncrypt(map,signKey));
    }

    private static DokypayService init(){
        DokypayConfiguration dokypayConfiguration = new DokypayConfiguration();
        dokypayConfiguration.setAppId("1001412384")
                .setAppSecret("bc2d5fc0c8d2442d86c9f4fd2d4a0b6b")
                .setMerchantId("10000")
                .setMerchantSecret("5f190aa12f6442e0be4efca58680355b")
                .setVersion("1.0")
        ;
        DokypayApiConfiguration dokypayApiConfiguration = new DokypayApiConfiguration();
        dokypayApiConfiguration.setAccountQuery("https://openapibeta.dokypay.com/account/query")
                .setUnifiedorder("https://gatewaybeta.dokypay.com/clientapi/unifiedorder")
                .setRefund("https://openapibeta.dokypay.com/refund")
        ;
        return new DokypayService(dokypayConfiguration,dokypayApiConfiguration);
    }



    public static void main(String[] args) {
        DokypayService dokypayService = init();
        doPay(dokypayService);
//        verfiyPayNoticeSign(dokypayService);
//        refund(dokypayService);


    }

    private static void refund(DokypayService dokypayService) {
        JSONObject map = new JSONObject();
        map.put("merchantId",dokypayService.getDokypayConfig().getMerchantId());
        map.put("tradeNo","20191021180851466010478825");
        map.put("amount","2.1");
		map.put("notifyUrl","https://www.baidu.com");
        map.put("currency","INR");
        DokypayResponse dokypayResponse = dokypayService.request(map.toJSONString(), dokypayService.getDokypayApiConfiguration().getRefund(), dokypayService.getDokypayConfig().getMerchantSecret());
        System.out.println(JSONObject.toJSONString(dokypayResponse));
        if(!dokypayService.verfiySign(dokypayResponse.getData(),dokypayResponse.getData().getString("sign"),dokypayService.getDokypayConfig().getMerchantSecret())){
            log.error("调用dokypay失败,请求参数:{},响应参数:{}",map.toJSONString(),JSONObject.toJSONString(dokypayResponse));
            throw new RuntimeException("dokypay响应验签失败!");
        }
    }

    private static void verfiyPayNoticeSign(DokypayService dokypayService) {
        PayNotifyEntity payNotifyEntity = new PayNotifyEntity();
        payNotifyEntity.setAmount("12.01")
                .setCreateTime("2019-01-09 14:53:05")
                .setCurrency("USD")
                .setMerTransNo("mtn8888888888")
                .setProcessAmount("170181.70")
                .setProcessCurrency("IDR")
                .setTransNo("201901091453055080016630")
                .setTransStatus(OrderStatusEnum.success)
                .setUpdateTime("2019-01-09 14:54:00")
                .setSign("f99b64ecd3ce69c8683df713163cbf90b4bfd710a0d66eb95ab301dce9363215");
        //商户密钥	5f190aa12f6442e0be4efca58680355b
        //应用号	1001412384
        //应用秘钥	bc2d5fc0c8d2442d86c9f4fd2d4a0b6b

        JSONObject jsonObject = JSONObject.parseObject("{\n" +
                "    \"amount\": \"12.01\",\n" +
                "    \"appId\": \"1000000126\",\n" +
                "    \"country\": \"ID\",\n" +
                "    \"currency\": \"USD\",\n" +
                "    \"description\": \"这是一个测试的商品\",\n" +
                "    \"merTransNo\": \"mtn8888888888\",\n" +
                "    \"notifyUrl\": \"http://yoursite.com/notifyurl\",\n" +
                "    \"pmId\": \"doku\",\n" +
                "    \"prodName\": \"southeast.asia\",\n" +
                "    \"returnUrl\": \"http://yoursite.com/returnurl\",\n" +
                "    \"sign\": \"d11d877c0f435f2f8a263eca22559af47fb5679b7b235483f2b6767cd9d1ce22\",\n" +
                "    \"version\": \"1.0\"\n" +
                "}");
        String sign = jsonObject.getString("sign");
        System.out.println(dokypayService.verfiySign(jsonObject,sign,"5f190aa12f6442e0be4efca58680355b"));
    }

    private static void doPay(DokypayService dokypayService) {
        JSONObject map = new JSONObject();
        map.put("amount","12");
        map.put("appId",dokypayService.getDokypayConfig().getAppId());
        map.put("currency","INR");
        map.put("country","IN");
        map.put("description","");
        map.put("merTransNo","12457812452ss78ssqwqyuresdfs");
        map.put("notifyUrl","http://4a523222.ngrok.io/callback/dokypay/pay");
        map.put("returnUrl","https://www.baidu.com");
        map.put("pmId","paytm");
        map.put("prodName", ProductConstants.SOUTHEAST_ASIA);
        JSONObject extInfo = new JSONObject();
//        extInfo.put("payerName","WELVY REDASURYANI");
//        extInfo.put("payerEmail","example@qq.com");
//        extInfo.put("payerMobile","08268686688");

        extInfo.put("cardNumber","4200000000000000");
        extInfo.put("firstName","test");
        extInfo.put("lastName","test");
        extInfo.put("expireDate","12/2036");
        extInfo.put("cvv","123");
        extInfo.put("payerEmail","1534196496@qq.com");

//        map.put("extInfo",extInfo);
        DokypayResponse dokypayResponse = dokypayService.request(map.toJSONString(), dokypayService.getDokypayApiConfiguration().getUnifiedorder(),dokypayService.getDokypayConfig().getAppSecret());
        System.out.println(dokypayService.verfiySign(dokypayResponse.getData(),dokypayResponse.getData().getString("sign"),dokypayService.getDokypayConfig().getAppSecret()));
        System.out.println(JSONObject.toJSONString(dokypayResponse));
    }
}
