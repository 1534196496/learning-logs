/**
  * Copyright 2019 bejson.com 
  */
package person.ll.dlocal.spring.boot.starter.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Auto-generated: 2019-11-01 10:43:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
@Accessors(chain = true)
public class Payer {
    private String name;
    private String userReference;
    private String email;
}