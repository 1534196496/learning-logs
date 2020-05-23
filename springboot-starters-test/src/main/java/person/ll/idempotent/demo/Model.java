package person.ll.idempotent.demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Model implements Serializable {
    private String p1;
    private Integer p2;
}
