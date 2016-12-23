package server;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.sshd.server.SshServer;

import client.SFtpWrapper;

public class StartServerRunner {

	public static void main(String[] args) {
		
		String configFilepath = "config/SERVER.cfg";
		
		File f = new File(configFilepath);
		if(!f.exists()){
			System.err.println("CONFIG FILE DOES NOT EXISTS AT config/SERVER.cfg");
			System.exit(1);
		}
		
		HashMap<String, String> cfgMap = ConfigReader.readConfig(configFilepath);
		
		String username = cfgMap.get("USERNAME");
		String password = cfgMap.get("PASSWORD");
		String port = cfgMap.get("PORT");
		
		try {
			
			SshServer sshd = SFtpTestUtil.startSFtpServer(username, password, Integer.parseInt(port));
			SFtpWrapper wrapper = new SFtpWrapper(username, password, "localhost", 14022);
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
