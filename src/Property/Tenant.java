package Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tenant {
	final String DB_URL = "jdbc:mysql://localhost:3306/propertysystem";
	final String DB_USER = "root";  // Use your MySQL username
	final String DB_PASSWORD = "Tobi4timi$";  // Use your MySQL password
	
	private String tenantID, name, email, password, landlordId, phone;
	
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
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
	
	//Gets tenantID from database
	public int getTenantIdFromDB() {
		int tenantId = 0;
		
		Connection conn = null;
		Statement tenantIdStmt = null;
		
		String tenantIdSQL = "SELECT tenant_ID FROM tenants WHERE email = '" + getEmail() + "' AND passwrd = '" + getPassword()+"'";
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			tenantIdStmt = conn.createStatement();
			
			ResultSet tenantIdRS = tenantIdStmt.executeQuery(tenantIdSQL);
			
			while(tenantIdRS.next()) {
				tenantId = tenantIdRS.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tenantId;
	}
	
	//Gets tenant user name from database
	public String getTenantNameFromDB() {
		int tenantId = getTenantIdFromDB();
		String tenantName = null;
			
		Connection conn = null;
		Statement tenantNameStmt = null;
			
		String tenantNameSQL = "SELECT full_name FROM tenants WHERE tenant_ID = " + tenantId;
			
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				
			tenantNameStmt = conn.createStatement();
			ResultSet tenantNameRS = tenantNameStmt.executeQuery(tenantNameSQL);
				
			while(tenantNameRS.next()) {
					tenantName = tenantNameRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tenantName;
	}

	//Gets landlords phone from database
	public String getTenantPhoneFromDB() {
		int tenantId = getTenantIdFromDB();
		String tenantPhone = null;
			
		Connection conn = null;
		Statement tenantPhoneStmt = null;
			
		String tenantPhoneSQL = "SELECT phone FROM tenants WHERE tenant_ID = " + tenantId;
			
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				
			tenantPhoneStmt = conn.createStatement();
			ResultSet tenantPhoneRS = tenantPhoneStmt.executeQuery(tenantPhoneSQL);
				
			while(tenantPhoneRS.next()) {
				tenantPhone = tenantPhoneRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return tenantPhone;
	}
	
	//Updates landlordID for tenant users
	public void updateLandlordIdForTenant(Lease lease, Tenant tenant) {
		int landlordId = lease.getLandlordIDFromLease(tenant);
		int tenantId = tenant.getTenantIdFromDB();
		
		Connection conn = null;
		Statement updateLandlordIdStmt = null;
		
		String updateLandlordIdSQL = "UPDATE tenants SET landlord_ID = " + landlordId + " WHERE tenant_ID = " + tenantId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			updateLandlordIdStmt = conn.createStatement();
			updateLandlordIdStmt.execute(updateLandlordIdSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Checks if tenant user is in database
	public boolean authenticateTenantUser(String inputedEmail, String inputedPassword) {
		String dbEmail = null;
		String dbPassword = null;
	
		Connection conn = null;
		Statement emailStmt = null;
		Statement passwordStmt = null;
		
		String emailSQL = "SELECT email FROM tenants WHERE email = '" + inputedEmail + "' AND passwrd = '" + inputedPassword + "'";
		String passwordSQL = "SELECT passwrd FROM tenants WHERE email = '" + inputedEmail + "' AND passwrd = '" + inputedPassword + "'";
		
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
	
	
	
	
}
