package person.ll.dlocal.spring.boot.starter.exception;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wll
 * Created Date:  2019/10/29
 * Description:   {类描述}
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
