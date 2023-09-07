package com.junsim.whereami.repository;

import com.junsim.whereami.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        return em.createQuery("select m from Member m where m.id = :memberId", Member.class)
                .setParameter("memberId", id)
                .getResultList()
                .stream().findAny();
    }


    public Optional<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findAny();
    }

    public Optional<Member> findByRpi(String rpiCode) {
        return em.createQuery("select m from Member m where m.rpiCode = :rpiCode", Member.class)
                .setParameter("rpiCode", rpiCode)
                .getResultList()
                .stream().findAny();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
