package Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tenant {
	final String DB_URL = "jdbc:mysql://localhost:3306/propertysystem";
	final String DB_USER = "root";  // Use your MySQL username
	final String DB_PASSWORD = "Tobi4timi$";  // Use your MySQL password
	
	private String tenantID, name, email, password, landlordId;
	private int phone;
	
	public String getTenantID() {
		return tenantID;
	}
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
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
	public String getLandlordId() {
		return landlordId;
	}
	public void setLandlordId(String landlordId) {
		this.landlordId = landlordId;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	
	//Adds new tenant user to the database
	public void insertTenantToDB(String name, String email, String password, String phone) {
		Connection conn = null;
		PreparedStatement tenantUserPstmt = null;
		
		//SQL Commands 
	    String tenantSQL = "INSERT INTO tenants (full_name, email, passwrd, phone) VALUES (?, ?, ?, ?)";
	    
	    try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			tenantUserPstmt = conn.prepareStatement(tenantSQL);
			tenantUserPstmt.setString(1, name);
			tenantUserPstmt.setString(2, email);
			tenantUserPstmt.setString(3, password);
			tenantUserPstmt.setString(4, phone);
			tenantUserPstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
