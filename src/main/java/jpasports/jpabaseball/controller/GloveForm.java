package jpasports.jpabaseball.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GloveForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String brand;
    private String position;
}
