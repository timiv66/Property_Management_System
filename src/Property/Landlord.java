package Property;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	public int getNumOfApartmentsForLandlord() {
		int landlordID = getLandlordIdFromDB();
		int numOfAparts = 0;
		
		Connection conn = null;
		Statement numOfApartsStmt = null;
		
		String numOfApartsSQL = "SELECT num_of_apartments FROM landlords WHERE landlord_ID = " + landlordID;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			numOfApartsStmt = conn.createStatement();
			ResultSet numOfApartsRS = numOfApartsStmt.executeQuery(numOfApartsSQL);
			
			while(numOfApartsRS.next()) {
				numOfAparts = numOfApartsRS.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return numOfAparts;
	}
	
	public int getNumOfTenantsForLandlord() {
		int landlordID = getLandlordIdFromDB();
		int numOfTenants = 0;
		
		Connection conn = null;
		Statement numOfTenantsStmt = null;
		
		String numOfTenantsSQL = "SELECT num_of_tenants FROM landlords WHERE landlord_ID = " + landlordID;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			numOfTenantsStmt = conn.createStatement();
			ResultSet numOfTenantsRS = numOfTenantsStmt.executeQuery(numOfTenantsSQL);
			
			while(numOfTenantsRS.next()) {
				numOfTenants = numOfTenantsRS.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return numOfTenants;
	}
	
	//inserts number of apartments a landlord has
	public void updateNumofApartments() {
		int landlordId = getLandlordIdFromDB();
		int numOfApart = 0;
		
		Connection conn = null;
		Statement countApartStmt = null;
		Statement updateNumofApartStmt = null;
		
		//SQL command that counts how many apartment a landlord has
		String countApartSQL = "SELECT COUNT(apartment_name) FROM apartments WHERE landlord_ID = " + landlordId;
		
		try {
			//Connection to database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			//Storing data from database into numOfApart string variable
			countApartStmt = conn.createStatement();
			ResultSet countApartRS = countApartStmt.executeQuery(countApartSQL);
			while(countApartRS.next()) {
				numOfApart = countApartRS.getInt(1);
			}
			//SQL command that updates how many apartment a landlord has
			String updateNumofApartSQL = "UPDATE landlords SET num_of_apartments = " + numOfApart + " WHERE landlord_ID = " + landlordId;
			
			//Executing update SQL query
			updateNumofApartStmt = conn.createStatement();
			updateNumofApartStmt.execute(updateNumofApartSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Updates total number of tenants a landlord has
	public void updateNumOfTenants() {
		int landlordId = getLandlordIdFromDB();
		int numOfTenants = 0;
		
		Connection conn = null;
		Statement countTenantsStmt = null;
		Statement updateNumofTenantsStmt = null;
		
		//SQL command that counts how many tenants a landlord has from joining the landlord and tenant table 
		String countTenantSQL = "SELECT COUNT(tenant_ID) FROM tenants INNER JOIN landlords ON tenants.landlord_ID = landlords.landlord_ID WHERE landlords.landlord_ID = " + landlordId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			//Storing data from database into numOfTenants string variable
			countTenantsStmt = conn.createStatement();
			ResultSet countTenantRS = countTenantsStmt.executeQuery(countTenantSQL);
			while(countTenantRS.next()) {
				numOfTenants = countTenantRS.getInt(1);
			}
			
			//Updates landlord table by inserting numOfTenants variable into database
			String updateNumofTenantsSQL = "UPDATE landlords SET num_of_tenants = " + numOfTenants + " WHERE landlord_ID = " + landlordId;
			updateNumofTenantsStmt = conn.createStatement();
			updateNumofTenantsStmt.execute(updateNumofTenantsSQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Updates a landlords email in the database
	public void updateLandlordEmail(String newEmail) {
		int landlordId = getLandlordIdFromDB();
		
		Connection conn = null;
		Statement updateLandlordEmailStmt = null;

		String updateLandlordEmailSQL = "UPDATE landlords SET email = '" + newEmail + "' WHERE landlord_ID = " + landlordId;
	
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			updateLandlordEmailStmt = conn.createStatement();
			updateLandlordEmailStmt.execute(updateLandlordEmailSQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Updates a landlords password in the database
	public void updateLandlordPasswrd(String newPasswrd) {
		int landlordId = getLandlordIdFromDB();
		
		Connection conn = null;
		Statement updateLandlordPasswrdStmt = null;

		String updateLandlordPasswrdSQL = "UPDATE landlords SET passwrd = '" + newPasswrd + "' WHERE landlord_ID = " + landlordId;
	
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			updateLandlordPasswrdStmt = conn.createStatement();
			updateLandlordPasswrdStmt.execute(updateLandlordPasswrdSQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Updates a landlords phone in the database
	public void updateLandlordPhone(String newPhone) {
		int landlordId = getLandlordIdFromDB();
		
		Connection conn = null;
		Statement updateLandlordPhoneStmt = null;

		String updateLandlordPhoneSQL = "UPDATE landlords SET phone = '" + newPhone + "' WHERE landlord_ID = " + landlordId;
	
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			updateLandlordPhoneStmt = conn.createStatement();
			updateLandlordPhoneStmt.execute(updateLandlordPhoneSQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	//Searches tenant and lease joined table based on search string
	public List<String> searchTenants(String searchStr){
		List<String> list = new ArrayList<String>();
		int landlordId = getLandlordIdFromDB();
		
		Connection conn = null;
		Statement searchStmt = null;
		
		String searchSQL = "SELECT distinct *\r\n" //Search query
				+ "FROM tenants INNER JOIN leases ON tenants.tenant_ID = leases.tenant_ID\r\n"
				+ "where full_name LIKE '" + searchStr + "%' AND tenants.landlord_ID = " + landlordId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			searchStmt = conn.createStatement();
			ResultSet searchRS = searchStmt.executeQuery(searchSQL);
			
			while(searchRS.next()) {
				//Fields of joined table
				String name = searchRS.getString("full_name");
				String email = searchRS.getString("email");
				String phone = searchRS.getString("phone");
				String apartName = searchRS.getString("apartment_name");
				int rent = searchRS.getInt("rent");
				
				Date startDate = searchRS.getDate("start_date");
				String startDateStr = String.valueOf(startDate);
				
				Date endDate = searchRS.getDate("end_date");
				String endDateStr = String.valueOf(endDate);
				
				//Inputting data from joined table into string value then adds string value to list
				String fullRecord = "Tenant name: " + name + ", Email: " + email + ", Phone: " + phone + ", "
						+ "\nApartment Name: " + apartName + ", Rent: " + rent + ", Lease Start Date: " + startDateStr + ", Lease End Date: " + endDateStr;
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
		int landlordId = getLandlordIdFromDB();
		
		Connection conn = null;
		Statement searchStmt = null;
		
		String searchSQL = "SELECT * FROM apartments INNER JOIN complextype ON " //Search query
				+ "apartments.apartment_type = complextype.apartment_type "
				+ "WHERE apartments.apartment_name LIKE '" + searchStr + "%' AND apartments.landlord_ID = " + landlordId ;
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
	
	public List<String> getAllApartmentNames(){
		List<String> list = new ArrayList<String>();
		int landlordId = getLandlordIdFromDB();
		
		Connection conn = null;
		Statement apartmentNamesStmt = null;
		
		String apartNameSQL = "SELECT apartment_name FROM apartments WHERE landlord_ID = " + landlordId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			apartmentNamesStmt = conn.createStatement();
			ResultSet apartmentNameRS = apartmentNamesStmt.executeQuery(apartNameSQL);
			
			while(apartmentNameRS.next()) {
				String a = apartmentNameRS.getString("apartment_name");
				list.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<String> getAllRequestID() {
		List<String> list = new ArrayList<String>();
		int landlordId = getLandlordIdFromDB();
		
		Connection conn = null;
		Statement requestsStmt = null;
		
		String requestsSQL = "SELECT request_ID FROM requests WHERE landlord_ID = " + landlordId;
		
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
	
}
