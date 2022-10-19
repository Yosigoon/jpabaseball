package jpasports.jpabaseball;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId(); //커맨드와 쿼리 분리원칙 id만 가지고 다음정보 조회가능하니 member 전체 return 하지 않는다
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
