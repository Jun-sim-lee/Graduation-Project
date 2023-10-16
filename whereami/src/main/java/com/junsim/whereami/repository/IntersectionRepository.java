package com.junsim.whereami.repository;

import com.junsim.whereami.domain.MemberResourceInterSection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IntersectionRepository {
    private final EntityManager em;

    public void save(MemberResourceInterSection interSection) {
        em.persist(interSection);
    }

    public Optional<MemberResourceInterSection> findByMemberIDAndResourceId(String email, Long resourceId) {
        return em.createQuery("select i from MemberResourceInterSection i " +
                "where i.member.email =:email AND i.resource.id=:resourceId", MemberResourceInterSection.class)
                .setParameter("email", email)
                .setParameter("resourceId", resourceId)
                .getResultList()
                .stream().findAny();
    }
    public void delete(MemberResourceInterSection interSection){
        em.remove(interSection);
    }
}
