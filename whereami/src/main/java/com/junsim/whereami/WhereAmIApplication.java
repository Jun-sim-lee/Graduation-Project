package com.junsim.whereami;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.junsim.whereami.map.RssMap.radioMap;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WhereAmIApplication {
	@PostConstruct
	public void initializeMap(){
		try {
			String path = System.getProperty("user.dir") + "/src/main/java/com/junsim/whereami/map/rss_value.txt";
			BufferedReader br = Files.newBufferedReader(Paths.get(path));
			String line = "";

			int x = 0;
			int y = 0;

			while((line = br.readLine()) != null){
				Integer[] temp = new Integer[81];
				line = line.substring(0, 323);
				String[] strArr = line.split(",");
				Integer t_num;

				for(int i = 0; i < strArr.length; i++){
					t_num = Integer.parseInt(strArr[i]);
					temp[i] = t_num;
				}

				radioMap[x][y] = temp;
				y++;
				if(y == 16){
					y = 0; x++;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}/*
		for(int i = 0; i < radioMap.length; i++){
			for(int j = 0; j < radioMap[0].length; j++){
				System.out.println();
				for(int k = 0; k < radioMap[0][0].length; k++){
					System.out.print(radioMap[i][j][k] + " ");
				}
			}
		}*/
	}
	public static void main(String[] args) {
		SpringApplication.run(WhereAmIApplication.class, args);
	}


}
