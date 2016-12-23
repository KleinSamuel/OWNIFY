package client;

import java.io.IOException;

/** Stand-alone-Programm zum Download von einem SFTP-Server sowie zum Entpacken von .zip und .gz */
public class DownloadFromSFtp {
	
	private String USERNAME;
	private String PASSWORD;
	private String IP;
	private String PORT;
	
	public DownloadFromSFtp(String username, String password, String ip, String port){
		this.USERNAME = username;
		this.PASSWORD = password;
		this.IP = ip;
		this.PORT = port;
	}
	
	public boolean downloadFromSFTP(String remotePath, String localDestinationPath){
		
		try {
			
			SFtpWrapper wrapper = new SFtpWrapper(this.USERNAME, this.PASSWORD, this.IP, Integer.parseInt(this.PORT));
		
			wrapper.downloadFile("files/music/Disturbed_The-Sound-Of-Silence.mp3", "/home/sam/Desktop/");
			
			wrapper.close();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getPORT() {
		return PORT;
	}

	public void setPORT(String pORT) {
		PORT = pORT;
	}
}