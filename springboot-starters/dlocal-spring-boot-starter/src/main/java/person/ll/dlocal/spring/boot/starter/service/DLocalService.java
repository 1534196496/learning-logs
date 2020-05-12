package person.ll.dlocal.spring.boot.starter.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import person.ll.dlocal.spring.boot.starter.config.DLocalApiConfiguration;
import person.ll.dlocal.spring.boot.starter.config.DLocalConfiguration;
import person.ll.dlocal.spring.boot.starter.constant.HeadNameConstant;
import person.ll.dlocal.spring.boot.starter.exception.DLocalException;
import person.ll.dlocal.spring.boot.starter.utils.SignatureCalculator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Project       common-sdk
 *
 * @author wll
 * Company:       Big Player Group
 * Created Date:  2019/10/28
 * Description:   {类描述}
 * Copyright @ 2017-2019 BIGPLAYER.GROUP – Confidential and Proprietary
 */
@Slf4j
public class DLocalService {
    private static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Getter
    private DLocalApiConfiguration dLocalApiConfiguration;
    @Getter
    private DLocalConfiguration dLocalConfiguration;

    public DLocalService(DLocalApiConfiguration dLocalApiConfiguration, DLocalConfiguration dLocalConfiguration) {
        this.dLocalApiConfiguration = dLocalApiConfiguration;
        this.dLocalConfiguration = dLocalConfiguration;
    }

    public String getCancelUrl(String authorizationId){
        return new StrBuilder(dLocalApiConfiguration.getPayments()).append("/").append(authorizationId).append("/cancel").toString();
    }

    public String post(String url,String body){
        HttpRequest request = HttpRequest.post(url)
                .addHeaders(getSignHeaders(body))
//                .header("X-Idempotency-Key", MD5Util.MD5(body))
                .body(body);
        HttpResponse response = request
                .execute();
        log.info("请求dlocal信息,请求url:{}\n,请求参数:{}\n,响应参数:{}\n",url, request,response);
        if(response == null || response.getStatus()!=HttpStatus.HTTP_OK){
            DLocalException error = DLocalException.error(1002, String.format("请求dlocal失败,请求url:%s\n,请求参数:%s\n,响应参数:%s\n", url, request, response));
            error.setResponse(response ==null?"":response.body());
            throw error;
        }
        return response.body();

    }
    public String get(String url, Map<String,Object> query)throws DLocalException{

        HttpResponse response = HttpRequest.get(url)
                .addHeaders(getSignHeaders(""))
                .form(query)
                .execute();
        if(response == null || response.getStatus()!=HttpStatus.HTTP_OK){
            String queryStr = new JSONObject(query).toString();
            log.error("请求dlocal失败,请求url:{}\n,请求参数:{}\n,响应参数:{}\n",url, queryStr,response==null?"空":response);
            DLocalException error = DLocalException.error(1001, String.format("请求dlocal失败,请求url:%s\n,请求参数:%s\n,响应参数:%s\n", url, queryStr, response == null ? "空" : response));
            error.setResponse(response ==null?"":response.body());
            throw error;
        }
        return response.body();
    }

    public static void main(String[] args) {
    }

    /**
     * 回调验签 工具
     * @param xLogin
     * @param xDate
     * @param body
     * @param authorization
     * @return
     */
    public boolean verfiySign(String xLogin, String xDate, String body,String authorization){
        try {
            return authorization.equals("V2-HMAC-SHA256, Signature: "+ SignatureCalculator.calculateSignature(xLogin,xDate,dLocalConfiguration.getSecretKey(),body));
        } catch (Exception e) {
            log.error("验证签名失败:{},xLogin:{},xDate:{},body:{},authorization:{}",e.getMessage(),xLogin,xDate,body,authorization);
           return false;
        }
    }










    private Map<String, String> getSignHeaders(String body) {
        String xDate = DateUtil.format(new Date(),ISO8601_FORMAT);
        String signature;
        try {
            signature = SignatureCalculator.calculateSignature(dLocalConfiguration.getXLogin(), xDate, dLocalConfiguration.getSecretKey(), body);
        } catch (Exception e) {
            throw new DLocalException(1002,e.getMessage());
        }
        Map<String,String> heads = new HashMap<>(5);
        heads.put(HeadNameConstant.HEAD_NAME_X_DATE,xDate);
        heads.put(HeadNameConstant.HEAD_NAME_X_LOGIN,dLocalConfiguration.getXLogin());
        heads.put(HeadNameConstant.HEAD_NAME_X_TRANS_KEY,dLocalConfiguration.getXTransKey());
        heads.put(HeadNameConstant.HEAD_NAME_Content_Type,"application/json");
        heads.put(HeadNameConstant.HEAD_NAME_Authorization,"V2-HMAC-SHA256,Signature:"+signature);
        return heads;
    }



}
