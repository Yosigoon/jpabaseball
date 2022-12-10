package jpasports.jpabaseball.service;

import jpasports.jpabaseball.domain.item.Item;
import jpasports.jpabaseball.reposittory.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional //Entity 사용 실무에서는 변경감지 사용! merge 사용하지말자!
    public void updateItem(Long itemId, UpdateItemDto updateItemDto) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.change(updateItemDto);
        //change 같은 의미있는 메소드를 사용하자 set 사용하지말자
        //findItem.setName(updateItemDto.getName());
        //findItem.setPrice(updateItemDto.getPrice());
        //findItem.setStockQuantity(updateItemDto.getStockQuantity());
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
