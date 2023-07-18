package com.junsim.whereami.repository;

import com.junsim.whereami.domain.Resource;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResourceRepository {
    private final EntityManager em;

    public Long save(Resource resource){
        em.persist(resource);
        return resource.getId();
    }
}
