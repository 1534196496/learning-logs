package person.ll.dlocal.spring.boot.starter.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Project       common-sdk
 *
 * @author wll
 * Company:       Big Player Group
 * Created Date:  2019/10/29
 * Description:   {类描述}
 * Copyright @ 2017-2019 BIGPLAYER.GROUP – Confidential and Proprietary
 */
public class DLocalException extends RuntimeException{

        private Integer code;
        private String msg;
        @Getter
        @Setter
        private String response;

        public DLocalException(Integer code, String msg) {
            super(msg);
            this.code = code;
            this.msg = msg;
        }
        public static DLocalException error(Integer code, String msg){
            return new DLocalException(code,msg);
        }
}
