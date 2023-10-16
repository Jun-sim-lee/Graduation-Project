package com.junsim.whereami.service;

import com.junsim.whereami.domain.Location;
import com.junsim.whereami.domain.Member;
import com.junsim.whereami.map.RssMap;
import com.junsim.whereami.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ApService {
    private final MemberRepository memberRepository;
    /*public void queueing(HashMap<String, Integer> rss){
        boolean[] visitedAp = new boolean[81];

        for(Map.Entry<String, Integer> entry : rss.entrySet()) {
            Integer apIndex = RssMap.indexMap.get(entry.getKey());

            if(apIndex == null)
                continue;

            visitedAp[apIndex] = true;
            RssMap.userQueue[apIndex][RssMap.queueCount % 3] = entry.getValue();
        }

        for(int i = 0; i < 81; i++){ // 측정되지 않은 값은 0으로 채워주자
            if(!visitedAp[i])
                RssMap.userQueue[i][RssMap.queueCount % 3] = 0;
        }

        RssMap.queueCount++; // Queue처럼 동작하게 하기 위한 카운트
    }*/

    /*public void fingerprint(HashMap<String, Integer> rss, String rpiCode){
        double min_rss = 999999999.0;
        int x = -1, y = -1;

        for (int i = 0; i < RssMap.radioMap.length; i++) {
            for (int j = 0; j < RssMap.radioMap[0].length; j++) {
                double euc_dist = 0;

                for (Map.Entry<String, Integer> entry : rss.entrySet()) {
                    Integer apIndex = RssMap.indexMap.get(entry.getKey());

                    if(apIndex == null)
                        continue;

                    double diff = 0; int tempSum = 0; int tempCnt = 0;
                    for (int l = 0; l < 3; l++) {
                        if (RssMap.userQueue[apIndex][l] != 0) {
                            tempCnt++;
                            tempSum += RssMap.userQueue[apIndex][l];
                        }
                    }

                    if (tempCnt != 0){
                        if(tempCnt == 1)
                            diff = RssMap.radioMap[i][j][apIndex] - tempSum;
                        else if(tempCnt == 2)
                            diff = RssMap.radioMap[i][j][apIndex] - (tempSum / tempCnt);
                        else if(tempCnt == 3)
                            diff = RssMap.radioMap[i][j][apIndex]
                                    - getMedian(RssMap.userQueue[apIndex][0], RssMap.userQueue[apIndex][1], RssMap.userQueue[apIndex][2]);

                        euc_dist += Math.pow(diff, 2);
                    }
                }

                if(min_rss > euc_dist) {
                    x = i;
                    y = j;
                    min_rss = euc_dist;
                }
            }
        }

        System.out.println("x = " + x + " y = " + y);
        saveLocation(new Location(x,y), rpiCode);
    }*/

    /*private int getMedian(int a, int b, int c){
        if((b > a && a > c) || (b < a && a < c))
            return a;
        else if(((a > b) && (b > c) || (a < b) && (b < c)))
            return b;
        else
            return c;
    }*/

    public void saveLocation(Location location, String code){
        Member member = memberRepository.findByRpi(code).get();
        member.changeLocation(location);
    }
}
