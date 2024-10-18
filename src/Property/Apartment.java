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
	public void insertApartToDB(String apartName, String location, int maxAmtOfTenants, String apartType, int stars, int landlordId) {
		Connection conn = null;
		PreparedStatement apartmentStmt = null;
		
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
			apartmentStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public static void main(String[] args) {
		Apartment apartment = new Apartment();
		List<String> list = apartment.getAllApartmentNames();
		
		
		for (String a : list) {
		    System.out.println(a);
		}
	}
	
	

}
