package com.junsim.whereami.repository;

import com.junsim.whereami.domain.Ask;
import com.junsim.whereami.domain.Resource;
import com.junsim.whereami.dto.AllResourceResponseDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResourceRepository {
    private final EntityManager em;

    public Long save(Resource resource){
        em.persist(resource);
        return resource.getId();
    }



    public List<Resource> findAll() {
        return em.createQuery("select r from Resource r", Resource.class)
                .getResultList();
    }

    public Optional<Resource> findById(Long id) {
        return em.createQuery("select r from Resource r where r.id=:id", Resource.class)
                .setParameter("id", id)
                .getResultList()
                .stream().findAny();
    }
}
