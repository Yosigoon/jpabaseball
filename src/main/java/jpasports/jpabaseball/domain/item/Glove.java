package jpasports.jpabaseball.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("G")
@Getter
@Setter
public class Glove extends Item {

    private String brand;
    private String position;
}
