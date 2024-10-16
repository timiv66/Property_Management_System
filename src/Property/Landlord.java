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
	
	private String landlordId, name, email, phone, password;
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
			e.printStackTrace();
		}
		
		return landlordID;
		
	}
	
	//Gets landlord user name from database
	public String getLandlordNameFromDB() {
		int landlordID = getLandlordIdFromDB();
		String landlordName = null;
		
		Connection conn = null;
		Statement landlordNameStmt = null;
		
		String landlordNameSQL = "SELECT full_name FROM landlords WHERE landlord_ID = " + landlordID;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			landlordNameStmt = conn.createStatement();
			ResultSet landlordNameRS = landlordNameStmt.executeQuery(landlordNameSQL);
			
			while(landlordNameRS.next()) {
				landlordName = landlordNameRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return landlordName;
	}
	
	//Gets landlords phone from database
	public String getLandlordPhoneFromDB() {
		int landlordID = getLandlordIdFromDB();
		String landlordPhone = null;
		
		Connection conn = null;
		Statement landlordPhoneStmt = null;
		
		String landlordPhoneSQL = "SELECT phone FROM landlords WHERE landlord_ID = " + landlordID;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			landlordPhoneStmt = conn.createStatement();
			ResultSet landlordPhoneRS = landlordPhoneStmt.executeQuery(landlordPhoneSQL);
			
			while(landlordPhoneRS.next()) {
				landlordPhone = landlordPhoneRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return landlordPhone;
	}
	
	//Checks if landlord user is in the database
	public boolean authenticateLandlordUser(String inputedEmail, String inputedPassword) {
		String dbEmail = null;
		String dbPassword = null;
	
		Connection conn = null;
		Statement emailStmt = null;
		Statement passwordStmt = null;
		
		String emailSQL = "SELECT email FROM landlords WHERE email = '" + inputedEmail + "' AND passwrd = '" + inputedPassword + "'";
		String passwordSQL = "SELECT passwrd FROM landlords WHERE email = '" + inputedEmail + "' AND passwrd = '" + inputedPassword + "'";
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			emailStmt = conn.createStatement();
			passwordStmt = conn.createStatement();
			
			ResultSet emailRS = emailStmt.executeQuery(emailSQL);
			ResultSet passwordRS = passwordStmt.executeQuery(passwordSQL);
			
			while(emailRS.next() && passwordRS.next()) {
				dbEmail = emailRS.getString(1);
				dbPassword = passwordRS.getString(1);
			}
			
			if(dbEmail.equals(null) || dbPassword.equals(null)) {
				return false;
			}else if(!dbEmail.contentEquals(inputedEmail) || !dbPassword.contentEquals(inputedPassword)) {
				return false;
			}else if(dbEmail.contentEquals(inputedEmail) && dbPassword.contentEquals(inputedPassword)) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static void main (String[] args) {
		
		
		
		
	}
}
