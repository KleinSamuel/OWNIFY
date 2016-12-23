package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogFileWriter {
	
	public static BufferedWriter writer;
	
	public static void open(String filepath){
		try {
			writer = new BufferedWriter(new FileWriter(filepath, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void write(String s){
		try {
			writer.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(){
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
