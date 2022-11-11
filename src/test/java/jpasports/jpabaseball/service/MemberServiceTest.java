package jpasports.jpabaseball.service;

import jpasports.jpabaseball.domain.Member;
import jpasports.jpabaseball.reposittory.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class) /* junit4 실행 시 스프링와 같이 엮어서 실행*/
@SpringBootTest /* spring 실행 상태 테스트 */
@Transactional /* test case 기본적으로 롤백 */
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    /*@Rollback(value = false)*/
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("yosi");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush(); // 영속성 컨텍스트 변겨내용 쿼리 반영 insert 확인
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("hw");

        Member member2 = new Member();
        member2.setName("hw");

        //when
        memberService.join(member1);
        memberService.join(member2);    //예외 발생!

        /*try {
            memberService.join(member2);    //예외 발생!
        } catch (IllegalStateException e) {
            return;
        }*/

        //then
        fail("예외 발생 (여기까지 오면 안된다!)");
    }

}