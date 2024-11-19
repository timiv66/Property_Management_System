package Property;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Apartment {
	final String DB_URL = "jdbc:mysql://localhost:3306/propertysystem";
	final String DB_USER = "root";  // Use your MySQL username
	final String DB_PASSWORD = "Tobi4timi$";  // Use your MySQL password
	
	private String apartName, location, landlordId, type;
	private int numOfTenants, numOfStars, maxAmtOfTenants;
	
	Landlord landlord = new Landlord();
	
	
	public String getName() {
		return apartName;
	}
	public void setName(String name) {
		this.apartName = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLandlordId() {
		return landlordId;
	}
	public void setLandlordId(String landlordId) {
		this.landlordId = landlordId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNumOfTenants() {
		return numOfTenants;
	}
	public void setNumOfTenants(int numOfTenants) {
		this.numOfTenants = numOfTenants;
	}
	public int getNumOfStars() {
		return numOfStars;
	}
	public void setNumOfStars(int numOfStars) {
		this.numOfStars = numOfStars;
	}
	public int getMaxAmtOfTenants() {
		return maxAmtOfTenants;
	}
	public void setMaxAmtOfTenants(int maxAmtOfTenants) {
		this.maxAmtOfTenants = maxAmtOfTenants;
	}
	
	
	//Adds new apartment to database
	public int insertApartToDB(String apartName, String location, int maxAmtOfTenants, String apartType, int stars, int landlordId) {
		Connection conn = null;
		PreparedStatement apartmentStmt = null;
		
		int update = 0;
		
		//SQL Commands 
	    String apartSQL = "INSERT INTO apartments (apartment_name, location, max_num_of_tenants, apartment_type, stars, landlord_ID) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			apartmentStmt = conn.prepareStatement(apartSQL);
			apartmentStmt.setString(1, apartName);
			apartmentStmt.setString(2, location);
			apartmentStmt.setInt(3, maxAmtOfTenants);
			apartmentStmt.setString(4, apartType);
			apartmentStmt.setInt(5, stars);
			apartmentStmt.setInt(6, landlordId);
			update = apartmentStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return update;
	}
	
	//Get all apartment names
	public List<String> getAllApartmentNames() {
		List<String> list = new ArrayList<String>();
		
		Connection conn = null;
		Statement apartmentNamesStmt = null;
		
		String apartNameSQL = "SELECT apartment_name FROM apartments";
		
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
	public String getApartmentNameFromDB() {
		String name = null;
		
		Connection conn = null;
		Statement apartmentNamesStmt = null;
		
		String apartNameSQL = "SELECT apartment_name FROM apartments WHERE apartment_name = '" + getName() + "'";
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			apartmentNamesStmt = conn.createStatement();
			ResultSet apartmentNameRS = apartmentNamesStmt.executeQuery(apartNameSQL);
			
			while(apartmentNameRS.next()) {
				name = apartmentNameRS.getString("apartment_name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	//Gets landlord_id from apartment table
	public int getLandlordIdFromApartments(Apartment apartment) {
		int landlordID = 0;
			
		Connection conn = null;
		Statement landlordIdStmt = null;
			
		String landlordIdSQL = "SELECT landlord_ID FROM apartments WHERE apartment_name = '" + apartment.getName() + "'" ;
			
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
	
	public void updateNumOfTenantsForApartment() {
		String apartName = getApartmentNameFromDB();
		int numOfTenants = 0;
		
		Connection conn = null;
		Statement countNumOfTenantsStmt = null;
		Statement updateNumOfTenantsStmt = null;
		
		String countNumOfTenantsSQL = "SELECT COUNT(tenant_ID) FROM leases WHERE apartment_name = '" + apartName + "'";
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			countNumOfTenantsStmt = conn.createStatement();
			ResultSet countNumOfTenantsRS = countNumOfTenantsStmt.executeQuery(countNumOfTenantsSQL);
			
			while(countNumOfTenantsRS.next()) {
				numOfTenants = countNumOfTenantsRS.getInt(1);
			}
			String updateNumOfTenantsSQL = "UPDATE apartments SET num_of_tenants = " + numOfTenants + " WHERE apartment_name = '" + apartName + "'";
			updateNumOfTenantsStmt = conn.createStatement();
			updateNumOfTenantsStmt.execute(updateNumOfTenantsSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Gets rent from joined table in database
		public int getRentFromDB(String apartName) {
			int rent = 0;
			Connection conn = null;
			Statement rentStmt = null;
			
			String rentSQL = "SELECT rent FROM complextype INNER JOIN apartments ON complextype.apartment_type = apartments.apartment_type WHERE apartment_name = '" + apartName + "'";
			
			try {
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				
				rentStmt = conn.createStatement();
				ResultSet rentRS = rentStmt.executeQuery(rentSQL);
				
				while(rentRS.next()) {
					rent = rentRS.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rent;
		}
	
		public int numOfTenantsFromDB(String apartName) {
			int numOfTenants = 0;
			Connection conn = null;
			Statement numOfTenantsStmt = null;
			
			String numOfTenantsSQL = "SELECT num_of_tenants FROM apartments WHERE apartment_name = '" + apartName + "'" ;
			
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
		
		public int maxNumOfTenantsFromDB(String apartName) {
			int maxNumOfTenants = 0;
			Connection conn = null;
			Statement maxNumOfTenantsStmt = null;
			
			String maxNumOfTenantsSQL = "SELECT max_num_of_tenants FROM apartments WHERE apartment_name = '" + apartName + "'" ;
			
			try {
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				
				maxNumOfTenantsStmt = conn.createStatement();
				ResultSet maxNumOfTenantsRS = maxNumOfTenantsStmt.executeQuery(maxNumOfTenantsSQL);
				
				while(maxNumOfTenantsRS.next()) {
					maxNumOfTenants = maxNumOfTenantsRS.getInt(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return maxNumOfTenants;
		}
	
	

}
