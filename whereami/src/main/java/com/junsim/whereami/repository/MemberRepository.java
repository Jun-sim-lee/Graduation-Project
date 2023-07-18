package com.junsim.whereami.repository;

import com.junsim.whereami.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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



}
