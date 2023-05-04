package com.example.messageTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MessageTestApplication {
	public static void fillIndex(HashMap<String, Integer> indexMap){
		indexMap.put("AA:AA:AA:AA:AA:AA", 0);
		indexMap.put("BB:BB:BB:BB:BB:BB", 1);
		indexMap.put("CC:CC:CC:CC:CC:CC", 2);
		indexMap.put("DD:DD:DD:DD:DD:DD", 3);
		indexMap.put("EE:EE:EE:EE:EE:EE", 4);

		// 미리 이렇게 index를 선언 해놓는다.
	}

	public static void fillRSS(HashMap<String, Integer> passedRSS){
		passedRSS.put("BB:BB:BB:BB:BB:BB", -39);
		passedRSS.put("CC:CC:CC:CC:CC:CC", -49);
		passedRSS.put("DD:DD:DD:DD:DD:DD", -42);
		passedRSS.put("EE:EE:EE:EE:EE:EE", -38);
	}

	public static void preconstructRadioMap(int[][][] radioMap){
		radioMap[0][0][0] = -30; radioMap[0][0][1] = -42; radioMap[0][0][2] = -50; radioMap[0][0][3] = -43; radioMap[0][0][4] = -39;
		radioMap[0][1][0] = -38; radioMap[0][1][1] = -45; radioMap[0][1][2] = -51; radioMap[0][1][3] = -48; radioMap[0][1][4] = -48;
		radioMap[1][0][0] = -40; radioMap[1][0][1] = -43; radioMap[1][0][3] = -49; radioMap[1][0][4] = -50;
		radioMap[1][1][0] = -35; radioMap[1][1][1] = -38; radioMap[1][1][2] = -46; radioMap[1][1][3] = -50; radioMap[1][1][4] = -52;

	}

	public static void main(String[] args) {
		HashMap<String, Integer> predefinedIndex = new HashMap<>();
		HashMap<String, Integer> passedRSS = new HashMap<>();
		int[][][] radioMap = new int[2][2][5];
		double min_mse = 9999999;
		int curr_x = 0, curr_y = 0;

		fillIndex(predefinedIndex);
		// 이런 RSS가 넘어왔다고 가정
		fillRSS(passedRSS);
		// Radio map을 미리 구성해놓는다.
		// 측정이 안되는 값은 기본적으로 0으로 간주..
		preconstructRadioMap(radioMap);

		// MSE를 계산해서 위치를 추정하는 알고리즘 (Fingerprint)
		for(int i = 0; i < radioMap.length; i++){
			for(int j = 0; j < radioMap[0].length; j++){
				double mse = 0;

				for (Map.Entry<String, Integer> entry : passedRSS.entrySet()){
					double diff = radioMap[i][j][predefinedIndex.get(entry.getKey())] - passedRSS.get(entry.getKey());
					mse += Math.pow(diff, 2);
				}
				// 이렇게 구현하면, RSS를 받아온 AP의 이름만 순회하며 계산이 가능하다.
				// 그런데, 측정이 안되고 넘어온 AP는 과연 배제해도 될까?

				System.out.println("mse = " + mse);
				if(min_mse > mse){
					min_mse = mse;
					curr_x = i;
					curr_y = j;
				}
			}
		}

		System.out.println("현재 좌표 : (" + curr_x + ", " + curr_y + ") 에 위치해 있습니다.");
	}

}
