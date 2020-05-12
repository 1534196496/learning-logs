package person.ll.dokypay.spring.boot.starter.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wll
 * Description:   {类描述}
 */
public class DokyPaySignUtil {
    private final static String EXTINFO_FIELD = "extInfo";
    public static String SIGN_FIELD = "sign";
    private final static String KEY_FIELD = "key";
    private final static String EQUAL_CHARACTER = "=";
    private final static String AND_CHARACTER = "&";

    public static String doEncrypt(Map<String,String> map, String signKey) {
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        StringBuilder originStr = new StringBuilder();
        for (String key : keys) {
            if(skipSign(key)){
                continue;
            }
            String value = map.get((key));
            if(StringUtils.isNotBlank(key.trim())&&StringUtils.isNotBlank(value.trim())){
               originStr.append(key).append(EQUAL_CHARACTER).append(value).append(AND_CHARACTER);
            }
        }
        originStr.append(KEY_FIELD).append(EQUAL_CHARACTER).append(signKey);
        return encryptSHA(originStr.toString());
    }

    private static boolean skipSign(String field){
        //extInfo与sign不参与签名字符串的拼接
        return EXTINFO_FIELD.equals(field.trim())||SIGN_FIELD.equals(field.trim());
    }

    private static  String encryptSHA(String data) {
        try {
            return Hex.encodeHexString(SHACoder.encodeSHA256(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
    }





}
