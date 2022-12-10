package jpasports.jpabaseball.domain.item;

import jpasports.jpabaseball.domain.Category;
import jpasports.jpabaseball.exception.NotEnoughStockException;
import jpasports.jpabaseball.service.UpdateItemDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /* 비즈니스 로직 */
    /**
     * stock 증가
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * @param quantity
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    /**
     * Item Update
     * @param updateItemDto
     */
    public void change(UpdateItemDto updateItemDto) {
        this.name = updateItemDto.getName();
        this.price = updateItemDto.getPrice();
        this.stockQuantity = updateItemDto.getStockQuantity();
    }
}
