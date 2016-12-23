package client;

public class DownloadFromServerTest {

	
	public static void main(String[] args) {
		
		String username = args[0];
		String password = args[1];
		String port = args[2];
		String ip = args[3];
		
		DownloadFromSFtp dl = new DownloadFromSFtp(username, password, ip, port);
		dl.downloadFromSFTP("/files/music/Disturbed_The-Sound-Of-Silence.mp3", "");
		
	}
	
}
