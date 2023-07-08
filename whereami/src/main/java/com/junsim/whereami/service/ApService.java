package com.junsim.whereami.service;

import com.junsim.whereami.map.RssMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApService {
    public void fingerprint(HashMap<String , Integer> rss){
        double min_rss = 99999999.0;
        int x = -1, y = -1;
        for (int i = 0; i < RssMap.radioMap.length; i++) {
            for (int j = 0; j < RssMap.radioMap[0].length; j++) {
                double mse = 0;
                for (Map.Entry<String ,Integer> entry : rss.entrySet()) {
                    if(RssMap.indexMap.get(entry.getKey()) == null){
                        continue;
                    }
                    double diff = RssMap.radioMap[i][j][RssMap.indexMap.get(entry.getKey())] - rss.get(entry.getKey());
                    mse += Math.pow(diff, 2);

                }

                if(min_rss > mse) {
                    x = i;
                    y = j;
                    min_rss = mse;
                }
            }
        }
        System.out.println("x = " + x + " y = " + y);
    }
}
