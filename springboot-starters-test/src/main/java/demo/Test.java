package demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Test implements Serializable {
    private String p1;
    private Integer p2;
}
