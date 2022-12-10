package jpasports.jpabaseball.reposittory;

import jpasports.jpabaseball.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(ObjectUtils.isEmpty(item.getId())){
            em.persist(item);
        }else{
            em.merge(item); //주의! -> 특정데이터만 변경하기 위해서는 새로 update 변경감지 만들자! 값이 없는 경우 null 업데이트 위험이 있다.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
