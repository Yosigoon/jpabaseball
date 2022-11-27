package jpasports.jpabaseball.service;

import jpasports.jpabaseball.domain.Address;
import jpasports.jpabaseball.domain.Member;
import jpasports.jpabaseball.domain.Order;
import jpasports.jpabaseball.domain.OrderStatus;
import jpasports.jpabaseball.domain.item.Bat;
import jpasports.jpabaseball.domain.item.Item;
import jpasports.jpabaseball.exception.NotEnoughStockException;
import jpasports.jpabaseball.reposittory.OrderRepository;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Bat bat = createBat("나무배트", 150000, 10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), bat.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수 확인", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량", 1000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고 차감", 8, bat.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBat("나무 배트", 10000, 10);

        int orderCount = 11;

        //when
        orderService.order(member.getId(), item.getId(), orderCount);

        //then
        fail("재소 수량 부족 예외 발생해야 한다.");


    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Bat item  = createBat("나무 배트", 1500, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("w주문 취소시 상태는 CANCEL.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("w주문이 취소된 상품은 재고 증가시킨다", 10, item.getStockQuantity());

    }

    private Bat createBat(String name, int price, int stockQuantity) {
        Bat bat = new Bat();
        bat.setName(name);
        bat.setPrice(price);
        bat.setStockQuantity(stockQuantity);
        em.persist(bat);
        return bat;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "한강", "123-1"));
        em.persist(member);
        return member;
    }

}
