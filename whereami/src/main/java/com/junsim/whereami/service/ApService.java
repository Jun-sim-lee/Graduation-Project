package com.junsim.whereami.service;

import com.junsim.whereami.map.RssMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApService {
    public void queueing(HashMap<String, Integer> rss){
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
    }

    public void fingerprint(){
        double min_rss = 999999999.0;
        int x = -1, y = -1;

        for (int i = 0; i < RssMap.radioMap.length; i++) {
            for (int j = 0; j < RssMap.radioMap[0].length; j++) {
                double euc_dist = 0;

                for (int k = 0; k < 81; k++) {
                    double diff = 0; int tempSum = 0; int tempCnt = 0;

                    for (int l = 0; l < 3; l++) {
                        if (RssMap.userQueue[k][l] != 0) {
                            tempCnt++;
                            tempSum += RssMap.userQueue[k][l];
                        }
                    }

                    if (tempSum != 0 || tempCnt != 0)
                        diff = RssMap.radioMap[i][j][k] - (tempSum / tempCnt);
                    euc_dist += Math.pow(diff, 2);
                }

                if(min_rss > euc_dist) {
                    x = i;
                    y = j;
                    min_rss = euc_dist;
                }
            }
        }

        System.out.println("=======================");
        System.out.println();
        for(int i = 0; i < 81; i++){
            System.out.println();
            System.out.print(i + " ");
            for(int j = 0; j < 3; j++){
                System.out.print(RssMap.userQueue[i][j] + " ");
            }
        }

        System.out.println("x = " + x + " y = " + y);
    }
}
