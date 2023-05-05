package com.junsim.whereami;

import com.junsim.whereami.domain.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void dbTest(){
        initService.test();
    }

    @RequiredArgsConstructor
    @Component
    @Transactional
    static class InitService {
        private final EntityManager em;
        public void test() {
            Location location = new Location();
            Member member = new Member("anfrhrl98@pusan.ac.kr", "1234", Authority.Student);
            Member member2 = new Member("ljh19980823@pusan.ac.kr", "1235", Authority.Student);
            Resource resource = new Resource("macBook", Authority.None, location);
            Ask ask = new Ask(member, resource);
            Ap ap1 = new Ap("우리집", -30);
            Ap ap2 = new Ap("남의집", -50);

            Greed greed = new Greed(0,0,ap1,ap2);

            em.persist(greed);

            em.persist(member);
            em.persist(member2);
            em.persist(resource);
            em.persist(ask);

            em.flush();
            em.clear();



        }
    }



}
