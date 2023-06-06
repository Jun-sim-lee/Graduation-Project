package service;

import com.junsim.whereami.map.RSSMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ApService {
    public void fingerprint(HashMap<String , Integer> rss){
        for (int i = 0; i < RSSMap.radioMap.length; i++) {
            for (int j = 0; j < RSSMap.radioMap[0].length; j++) {
                double mse = 0;
                for (Map.Entry<String ,Integer> entry : rss.entrySet()) {
                    if(RSSMap.indexMap.get(entry.getKey()) == null){
                        continue;
                    }
                    double diff = RSSMap.radioMap[i][j][RSSMap.indexMap.get(entry.getKey())] - rss.get(entry.getKey());
                    mse += Math.pow(diff, 2);

                }

                System.out.println(i + ", " + j + "'s mse = " + mse);
            }
        }
    }
}
