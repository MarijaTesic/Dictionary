package zavrsni;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Recnik {
	
	private HashMap<String,String>recnik;
	private String connectionString="jdbc:sqlite:C:\\Users\\User\\Downloads\\Dictionary.db";
	private Connection con;
	
	public Recnik() {
		recnik=new HashMap<String,String>();
	}
	
	public HashMap<String, String> getRecnik() {
		return recnik;
	}

	public void setRecnik(HashMap<String, String> recnik) {
		this.recnik = recnik;
	}

	public void connect() {
		try {
			con=DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			if(con!=null && con.isClosed()) con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void popuniRecnik() {
		try {
			Connection con=DriverManager.getConnection(connectionString);
			Statement stm=con.createStatement();
			String upit="SELECT word, definition FROM entries";
			ResultSet rs=stm.executeQuery(upit);
			
			while(rs.next()) {
				recnik.put(rs.getString("word").toLowerCase(),rs.getString("definition"));
			}
			rs.close();
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean pronadjiRec(String r) {
		r=r.toLowerCase();
		if(recnik.containsKey(r)) return true; 
		else return false;
		
	}
	//pravim tabelu u koju cu da stavljam nove reci
	public void dodajNoveReci(ArrayList<String>reci2) {
		try {
			Connection con=DriverManager.getConnection(connectionString);
			Statement stm=con.createStatement();
			
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "NoveReci", null);
			if (!tables.next()) {
				String upit="CREATE TABLE \"NoveReci\" (\r\n" + 
						"	\"Rec\"	TEXT,\r\n" + 
						"	\"Definicija\"	TEXT,\r\n" + 
						"	PRIMARY KEY(\"Rec\")\r\n" + 
						")";
				stm.executeUpdate(upit);
			}
			else {
				String upit="DROP TABLE NoveReci;";
				stm.executeUpdate(upit);
			}

			String upit2="INSERT INTO NoveReci(Rec)"
					+ "VALUES(?)";
			PreparedStatement ps = con.prepareStatement(upit2);
			
			for(int i=0;i<reci2.size();i++) {
				ps.setString(1, reci2.get(i));
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
}

