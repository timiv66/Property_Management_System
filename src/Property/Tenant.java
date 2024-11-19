package Property;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<String> getAllRequestIDForOneTenant() {
		List<String> list = new ArrayList<String>();
		int tenantId = getTenantIdFromDB();
		
		Connection conn = null;
		Statement requestsStmt = null;
		
		String requestsSQL = "SELECT request_ID FROM requests WHERE tenant_ID = " + tenantId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			requestsStmt = conn.createStatement();
			ResultSet requestsRS = requestsStmt.executeQuery(requestsSQL);
			
			while(requestsRS.next()) {
				String a = requestsRS.getString("request_ID");
				list.add(a);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
	
	public void updateNumOfTenantsForLandandlord(Lease lease, Tenant tenant) {
		int landlordId = lease.getLandlordIDFromLease(tenant);
		int numOfTenants = 0;
		
		Connection conn = null;
		Statement countNumOfTenansStmt = null;
		Statement updateNumOfTenansStmt = null;
		
		String countSQL = "select count(tenant_ID) from leases where landlord_ID = " + landlordId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			countNumOfTenansStmt = conn.createStatement();
			ResultSet tenantCountRS = countNumOfTenansStmt.executeQuery(countSQL);
			
			while(tenantCountRS.next()) {
					numOfTenants = tenantCountRS.getInt(1);
			}
			
			String updateNumofTenantsSQL = "UPDATE landlords SET num_of_tenants = " + numOfTenants + " WHERE landlord_ID = " + landlordId;
			updateNumOfTenansStmt = conn.createStatement();
			updateNumOfTenansStmt.execute(updateNumofTenantsSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateTenantEmail(String newEmail) {
		int tenantId = getTenantIdFromDB();
		
		Connection conn = null;
		Statement updateTenantEmailStmt = null;

		String updateTenantEmailSQL = "UPDATE tenants SET email = '" + newEmail + "' WHERE tenant_ID = " + tenantId;
	
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			updateTenantEmailStmt = conn.createStatement();
			updateTenantEmailStmt.execute(updateTenantEmailSQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateTenantPasswrd(String newPasswrd) {
		int tenantId = getTenantIdFromDB();
		
		Connection conn = null;
		Statement updateTenantPasswrdStmt = null;

		String updateTenantPasswrdSQL = "UPDATE tenants SET passwrd = '" + newPasswrd + "' WHERE tenant_ID = " + tenantId;
	
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			updateTenantPasswrdStmt = conn.createStatement();
			updateTenantPasswrdStmt.execute(updateTenantPasswrdSQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateTenantPhone(String newPhone) {
		int tenantId = getTenantIdFromDB();
		
		Connection conn = null;
		Statement updateTenantPhoneStmt = null;

		String updateTenantPhoneSQL = "UPDATE tenants SET phone = '" + newPhone + "' WHERE tenant_ID = " + tenantId;
	
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			updateTenantPhoneStmt = conn.createStatement();
			updateTenantPhoneStmt.execute(updateTenantPhoneSQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> searchLandlords(String searchStr){
		List<String> list = new ArrayList<String>();
		
		Connection conn = null;
		Statement searchStmt = null;
		
		String searchSQL = "SELECT * FROM landlords WHERE full_name LIKE '" + searchStr + "%'";
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			searchStmt = conn.createStatement();
			ResultSet searchRS = searchStmt.executeQuery(searchSQL);
			
			while(searchRS.next()) {
				//Fields of table
				String name = searchRS.getString("full_name");
				String email = searchRS.getString("email");
				String phone = searchRS.getString("phone");
				int numOfTenants = searchRS.getInt("num_of_tenants");
				int numOfAparts = searchRS.getInt("num_of_apartments");
				
				//Inputting data from joined table into string value then adds string value to list
				String fullRecord = "Landlord name: " + name + ", Email: " + email + ", Phone: " + phone + ", "
						+ "\nNumber of Tenants: " + numOfTenants + ", Number of Apartments: " + numOfAparts; 
				list.add(fullRecord);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	//Searches apartment and complextype joined table based on search string
		public List<String> searchApartments(String searchStr){
			List<String> list = new ArrayList<String>();
			
			
			Connection conn = null;
			Statement searchStmt = null;
			
			String searchSQL = "SELECT * FROM apartments INNER JOIN complextype ON " //Search query
					+ "apartments.apartment_type = complextype.apartment_type "
					+ "WHERE apartments.apartment_name LIKE '" + searchStr + "%'" ;
			try {
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				searchStmt = conn.createStatement();
				ResultSet searchRS = searchStmt.executeQuery(searchSQL);
				
				while(searchRS.next()) {
					
					//Fields of joined table 
					String apartName = searchRS.getString("apartment_name");
					String apartType = searchRS.getString("apartment_type");
					String location = searchRS.getString("location");
					int numofTenants = searchRS.getInt("num_of_tenants");
					int maxNumOfTenants = searchRS.getInt("max_num_of_tenants");
					int stars = searchRS.getInt("stars");
					int rent = searchRS.getInt("rent");
					
					//Inputting data from joined table into string value then adds string value to list
					String fullRecord = "Apartment name: " + apartName + ", Apartment Type: " + apartType + ", Location: " + location + ", "
							+ "\nNumber of Tenants: " + numofTenants + ", Max Capacity: " + maxNumOfTenants + ", Stars: " + stars + ", Rent: " + rent;
					list.add(fullRecord);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return list;
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
