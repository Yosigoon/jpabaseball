package jpasports.jpabaseball.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("U")
@Getter
@Setter
public class Uniform extends Item {

    private String team;
    private String etc;
}
