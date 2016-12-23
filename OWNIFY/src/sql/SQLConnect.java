package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class SQLConnect {
	
	public static final String URL = "jdbc:mysql://192.168.178.54:3306/sftp_server";
	public static final String USERNAME = "user";
	public static final String PASSWORD = "newpassword";
	
	private boolean IS_CONNECTED = false;
	private Connection connection;
	
	public void connectToDatabase(){
		
		System.out.println("Connecting to database..");
		
		try {

			this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Successfully connected to database!");
			this.IS_CONNECTED = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot connect to database!");
		}
		
	
	}
	
	public HashMap<String, String> getArtistListFromSQL(){
		
		if(!IS_CONNECTED){
			System.err.println("NO CONNECTION ESTABLISHED!");
			System.exit(0);
		}
		
		HashMap<String, String> map = new HashMap<>();
		
		try {
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from ARTISTS");

			ResultSetMetaData rsmd = rs.getMetaData();
			
			while(rs.next()){
				map.put(rs.getString(2), rs.getString(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public int getIdForArtist(String artistName){
		
		if(!IS_CONNECTED){
			System.err.println("NO CONNECTION ESTABLISHED!");
			System.exit(0);
		}
		
		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select ID from ARTISTS where Name=\""+artistName+"\"");

			ResultSetMetaData rsmd = rs.getMetaData();
			
			while(rs.next()){
				return Integer.parseInt(rs.getString(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ArrayList<String> getSongsFromArtist(int artistID){
		
		if(!IS_CONNECTED){
			System.err.println("NO CONNECTION ESTABLISHED!");
			System.exit(0);
		}
		
		ArrayList<String> list = new ArrayList<>();
		
		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from SONGS where Artist="+artistID);

			ResultSetMetaData rsmd = rs.getMetaData();
			
			while(rs.next()){
				list.add(rs.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public static void main(String[] args) {

		SQLConnect sc = new SQLConnect();
		sc.connectToDatabase();
		HashMap<String, String> map = sc.getArtistListFromSQL();
		
		for(Entry<String, String> entry : map.entrySet()){
			System.out.println(entry.getKey());
			for(String s : sc.getSongsFromArtist(sc.getIdForArtist(entry.getKey()))){
				System.out.println("\t"+s);
			}
		}

	}

}
