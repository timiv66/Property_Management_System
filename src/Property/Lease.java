package Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

public class Lease {
	
	final String DB_URL = "jdbc:mysql://localhost:3306/propertysystem";
	final String DB_USER = "root";  // Use your MySQL username
	final String DB_PASSWORD = "Tobi4timi$";  // Use your MySQL password
	
	String leaseID, tenantID, landlordID, apartment_name, date;
	int length;
	
	public String getLeaseID() {
		return leaseID;
	}
	public void setLeaseID(String leaseID) {
		this.leaseID = leaseID;
	}
	public String getTenantID() {
		return tenantID;
	}
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}
	public String getLandlordID() {
		return landlordID;
	}
	public void setLandlordID(String landlordID) {
		this.landlordID = landlordID;
	}
	public String getApartment_name() {
		return apartment_name;
	}
	public void setApartment_name(String apartment_name) {
		this.apartment_name = apartment_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
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
	
	//Creates new lease and adds it to database
	public void createLease(Tenant tenant, Apartment apartment, String apartmentName, int length, int rent, String start_date, String end_date) {
		String leaseId = generateLeaseID();
		int tenantId = tenant.getTenantIdFromDB();
		int landlordId = apartment.getLandlordIdFromApartments(apartment);
		
		Connection conn = null;
		PreparedStatement newLeasePstmt = null;
		
		//SQL Commands 
	    String leaseSQL = "INSERT INTO leases (lease_ID, apartment_name, length, rent, start_date, end_date, tenant_ID, landlord_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			newLeasePstmt = conn.prepareStatement(leaseSQL);
			newLeasePstmt.setString(1, leaseId);
			newLeasePstmt.setString(2, apartmentName);
			newLeasePstmt.setInt(3, length);
			newLeasePstmt.setInt(4, rent);
			newLeasePstmt.setString(5, start_date);
			newLeasePstmt.setString(6, end_date);
			newLeasePstmt.setInt(7, tenantId);
			newLeasePstmt.setInt(8, landlordId);
			newLeasePstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Gets landlordID from lease table in database
	public int getLandlordIDFromLease(Tenant tenant) {
		int landlordId = 0;
		Connection conn = null;
		Statement landlordIdStmt = null;
		
		String landlordIdSQL = "SELECT landlord_ID FROM leases WHERE tenant_ID = " + tenant.getTenantIdFromDB();
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			landlordIdStmt = conn.createStatement();
			ResultSet landlordIdRS = landlordIdStmt.executeQuery(landlordIdSQL);
			
			while(landlordIdRS.next()) {
				landlordId = landlordIdRS.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return landlordId;
	}
	
	
	//Creates leaseID for database
	public static String generateLeaseID() {
        int length = 10;
        StringBuilder randomStringBuilder = new StringBuilder(length);
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            int randomNumber = rand.nextInt(10); 
            randomStringBuilder.append(randomNumber);
        }
        return randomStringBuilder.toString();
    }
}
