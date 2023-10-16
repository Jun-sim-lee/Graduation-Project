package com.junsim.whereami.service;

import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.junsim.whereami.domain.Member;
import com.junsim.whereami.dto.MapResponseDTO;
import com.junsim.whereami.dto.PathResponseDTO;
import com.junsim.whereami.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.junsim.whereami.map.RssMap.realMap;
import static com.junsim.whereami.utility.SecurityUtility.currentMember;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MapService {
    private final MemberRepository memberRepository;
    private final ResourceService resourceService;
    final static int INF = 99999999;
    final static int[] DX = {-1, 0, 1, 0};
    final static int[] DY = {0, 1, 0, -1};
    @Getter
    public static class Tuple implements Comparable<Tuple> {
        Integer x;
        Integer y;
        Integer cost;

        public Tuple(Integer x, Integer y, Integer cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        @Override
        public int compareTo(Tuple target) {
            return this.cost >= target.cost ? 1 : -1;
        }
    }

    @Getter
    public static class Pair{
        Integer x;
        Integer y;

        public Pair(Integer x, Integer y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{" +
                    +x +
                    ", " + y +
                    '}';
        }
    }

    private static void initiateDist(int[][] dist){
        for(int i = 0; i < 18; i++)
            for(int j = 0; j < 16; j++)
                dist[i][j] = INF;
    }

    private static void initiatePaths(ArrayList<ArrayList<Pair>> paths){
        for(int i = 0; i < 18; i++){
            ArrayList<Pair> tempArray = new ArrayList<>();
            for(int j = 0; j < 16; j++){
                tempArray.add(new Pair(0, 0));
            }
            paths.add(tempArray);
        }
    }

    private static void dijkstra(Tuple start, int[][] dist, int[][] radioMap, ArrayList<ArrayList<Pair>> paths){
        PriorityQueue<Tuple> dijkQueue = new PriorityQueue<>();
        dijkQueue.add(start);
        dist[start.x][start.y] = 1;

        while(!dijkQueue.isEmpty()){
            Integer currentX = dijkQueue.peek().x;
            Integer currentY = dijkQueue.peek().y;
            Integer currentCost = dijkQueue.peek().cost;
            dijkQueue.remove();

            for(int i = 0; i < 4; i++){
                Integer nx = currentX + DX[i];
                Integer ny = currentY + DY[i];

                if((nx >= 0 && nx < 18) && (ny >= 0 && ny < 16) && radioMap[nx][ny] != -1) {
                    Integer nextCost = currentCost + 1;

                    if (dist[nx][ny] > nextCost) {
                        dist[nx][ny] = nextCost;
                        paths.get(nx).set(ny, new Pair(currentX, currentY));
                        dijkQueue.add(new Tuple(nx, ny, nextCost));
                    }
                }
            }
        }
    }

    public PathResponseDTO findPath(Pair endPoint) {
        Member member = memberRepository.findByEmail(currentMember()).get();
        Pair startPoint = new Pair(member.getLocation().getX(), member.getLocation().getY());
        int[][] dist = new int[18][16];
        ArrayList<ArrayList<Pair>> paths = new ArrayList<>(new ArrayList<>());
        ArrayList<Pair> pathVector = new ArrayList<>();

        initiateDist(dist);
        initiatePaths(paths);
        dijkstra(new Tuple(startPoint.x, startPoint.y, 1), dist, realMap, paths);

        Pair cursor = endPoint;

        while(!cursor.toString().equals(startPoint.toString())){
            pathVector.add(cursor);
            cursor = paths.get(cursor.x).get(cursor.y);
        }

        return new PathResponseDTO(pathVector);
    }

    public MapResponseDTO myLocation() throws JsonProcessingException {
        Member member = memberRepository.findByEmail(currentMember()).get();
        MapResponseDTO mapResponseDTO = new MapResponseDTO(member.getLocation().getX(), member.getLocation().getY());
        resourceService.nearResource(member);
        return mapResponseDTO;
    }

}

