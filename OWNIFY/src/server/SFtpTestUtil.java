package server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;

public class SFtpTestUtil {

	public static SshServer startSFtpServer(final String benutzername, final String passwort, int port) throws IOException {
		
		SshServer sftpServer = SshServer.setUpDefaultServer();
		sftpServer.setPort(port);
		sftpServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("config/hostkey.ser")));
		sftpServer.setSubsystemFactories(Arrays.<NamedFactory<Command>> asList(new SftpSubsystemFactory()));
		
		sftpServer.getProperties().put(SshServer.IDLE_TIMEOUT, Integer.MAX_VALUE);
		
		String logFile = "log/REQUESTS.log";
		
		sftpServer.setPasswordAuthenticator(new PasswordAuthenticator() {
			@Override
			public boolean authenticate(String username, String password, ServerSession session) {
				
				InetSocketAddress socketAddress = (InetSocketAddress) session.getClientAddress();
				InetAddress inetAddress = socketAddress.getAddress();
				
				LogFileWriter.open(logFile);
				LogFileWriter.write(getDateAsString()+"\t"+inetAddress.getHostAddress()+"\t"+username+"\t"+password+"\t");
				
				System.out.println("\n### NEW CONNECTION REQUEST ###");
				System.out.println("TIME:\t\t"+getDateAsString());
				System.out.println("FROM:\t\t"+inetAddress.getHostAddress());
				System.out.println("USERNAME:\t"+username);
				System.out.println("PASSWORD:\t"+password);
				System.out.print("ACCEPTED:\t");
				
				if(benutzername.equals(username) && passwort.equals(password)){
					System.out.println("YES");
					System.out.println("##############################\n");
					LogFileWriter.write("YES\n");
					LogFileWriter.close();
					return true;
				}else{
					System.out.println("NO");
					System.out.println("##############################\n");
					LogFileWriter.write("YES\n");
					LogFileWriter.close();
					return false;
				}
			}
		});
		sftpServer.start();
		return sftpServer;
	}
	
	public static String getDateAsString(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		return dateFormat.format(cal.getTime());
	}
}
