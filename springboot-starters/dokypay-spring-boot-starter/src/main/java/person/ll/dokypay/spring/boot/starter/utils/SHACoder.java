package person.ll.dokypay.spring.boot.starter.utils;

import java.security.MessageDigest;

public class SHACoder {
    /**
     * SHA-1消息摘要
     * @param data 待处理的消息摘要
     *
     * **/
    public static byte[] encodeSHA(byte[] data) throws Exception{
        
        //初始化MessageDisgest
        MessageDigest md= MessageDigest.getInstance("SHA");
        
        return md.digest(data);
    }
    /**
     * SHA-256消息摘要
     * @param data 待处理的消息摘要
     *
     * **/
    public static byte[] encodeSHA256(byte[] data) throws Exception{
        
        //初始化MessageDisgest
        MessageDigest md= MessageDigest.getInstance("SHA-256");
        
        return md.digest(data);
    }
    
    /**
     * SHA-384消息摘要
     * @param data 待处理的消息摘要
     *
     * **/
    public static byte[] encodeSHA384(byte[] data) throws Exception{
        
        //初始化MessageDisgest
        MessageDigest md= MessageDigest.getInstance("SHA-384");
        
        return md.digest(data);
    }
    
    /**
     * SHA-512消息摘要
     * @param data 待处理的消息摘要
     *
     * **/
    public static byte[] encodeSHA512(byte[] data) throws Exception{
        
        //初始化MessageDisgest
        MessageDigest md= MessageDigest.getInstance("SHA-512");
        
        return md.digest(data);
    }
}