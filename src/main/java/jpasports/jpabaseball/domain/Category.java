package jpasports.jpabaseball.domain;

import jpasports.jpabaseball.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "categoty_id")
    private Long id;

    private String name;

    @ManyToMany //실무에서는 이것저것 추가 데이터들이 복잡하기떄문에 사용 NO!
    @JoinTable(name = "category_item"
            , joinColumns = @JoinColumn(name = "category_id")
            , inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    //아래는 셀프로 연관관계 적용
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    /* 연관관계 메소드 */
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
