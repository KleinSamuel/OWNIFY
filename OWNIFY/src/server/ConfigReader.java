package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ConfigReader {

	public static HashMap<String, String> readConfig(String path){
		
		HashMap<String, String> cfgMap = new HashMap<>();
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			String line = null;
			
			while((line = br.readLine()) != null){
				if(!line.startsWith("#")){
					String[] lineSplitted = line.split("\t");
					cfgMap.put(lineSplitted[0], lineSplitted[1]);
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cfgMap;
	}
	
}
