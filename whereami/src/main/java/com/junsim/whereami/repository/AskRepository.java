package com.junsim.whereami.repository;

import com.junsim.whereami.domain.Ask;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AskRepository {
    private final EntityManager em;

    public Long save(Ask ask){
        em.persist(ask);
        return ask.getId();
    }

    public Optional<Ask> findById(Long id) {
        return em.createQuery("select a from Ask a where a.id=:id", Ask.class)
                .setParameter("id", id)
                .getResultList().stream().findAny();
    }

    public List<Ask> findAllAsk() {
        return em.createQuery("select a from Ask a", Ask.class)
                .getResultList();
    }

    public void delete(Ask ask) {
        em.remove(ask);
    }

}
