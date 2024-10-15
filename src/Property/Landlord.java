package Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Landlord {
	final String DB_URL = "jdbc:mysql://localhost:3306/propertysystem";
	final String DB_USER = "root";  // Use your MySQL username
	final String DB_PASSWORD = "Tobi4timi$";  // Use your MySQL password
	
	private String landlordId, name, email,phone, password;
	private int numOfTenants;
	
	
	public String getLandlordId() {
		return landlordId;
	}
	public void setLandlordId(String landlordId) {
		this.landlordId = landlordId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getNumOfTenants() {
		return numOfTenants;
	}
	public void setNumOfTenants(int numOfTenants) {
		this.numOfTenants = numOfTenants;
	}
	
	
	//Adds new tenant user to the database
	public void insertLandlordToDB(String name, String email, String password, String phone) {
		Connection conn = null;
		PreparedStatement landlordUserPstmt = null;
			
		//SQL Commands 
		String landlordSQL = "INSERT INTO landlords (full_name, email, passwrd, phone) VALUES (?, ?, ?, ?)";
		    
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			landlordUserPstmt = conn.prepareStatement(landlordSQL);
			landlordUserPstmt.setString(1, name);
			landlordUserPstmt.setString(2, email);
			landlordUserPstmt.setString(3, password);
			landlordUserPstmt.setString(4, phone);
			landlordUserPstmt.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
		}
	}
	
	//Gets landlord_id from database
	public int getLandlordIdFromDB() {
		int landlordID = 0;
		
		Connection conn = null;
		Statement landlordIdStmt = null;
		
		String landlordIdSQL = "SELECT landlord_ID FROM landlords WHERE email = '" + getEmail() + "' AND passwrd = '" + getPassword()+"'";
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			landlordIdStmt = conn.createStatement();
			
			ResultSet landlordIdRS = landlordIdStmt.executeQuery(landlordIdSQL);
			
			while(landlordIdRS.next()) {
				landlordID = landlordIdRS.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return landlordID;
		
	}
}
