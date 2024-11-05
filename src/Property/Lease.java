package Property;

import java.sql.Connection;
import java.sql.Date;
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
	Tenant tenant;
	Lease lease;
	
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
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	public Lease getLease() {
		return lease;
	}
	public void setLease(Lease lease) {
		this.lease = lease;
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
	public String getLeaseIdFromLease(Tenant tenant) {
		String leaseId = null;
		Connection conn = null;
		Statement leaseIdStmt = null;
		
		String leaseIdSQL = "SELECT lease_ID FROM leases WHERE tenant_ID = " + tenant.getTenantIdFromDB();
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			leaseIdStmt = conn.createStatement();
			ResultSet leaseIdRS = leaseIdStmt.executeQuery(leaseIdSQL);
			
			while(leaseIdRS.next()) {
				leaseId = leaseIdRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return leaseId;
	}
	
	public String getApartNameFromLease(Tenant tenant) {
		String apartName = null;
		Connection conn = null;
		Statement apartNameStmt = null;
		
		String apartNameSQL = "SELECT apartment_name FROM leases WHERE tenant_ID = " + tenant.getTenantIdFromDB();
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			apartNameStmt = conn.createStatement();
			ResultSet apartNameRS = apartNameStmt.executeQuery(apartNameSQL);
			
			while(apartNameRS.next()) {
				apartName = apartNameRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return apartName;
	}
	
	public int getLeaseLengthFromLease(Tenant tenant) {
		int leaseLength = 0;
		Connection conn = null;
		Statement leaseLengthStmt = null;
		
		String leaseLengthSQL = "SELECT length FROM leases WHERE tenant_ID = " + tenant.getTenantIdFromDB();
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			leaseLengthStmt = conn.createStatement();
			ResultSet leaseLengthRS = leaseLengthStmt.executeQuery(leaseLengthSQL);
			
			while(leaseLengthRS.next()) {
				leaseLength = leaseLengthRS.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return leaseLength;
	}
	
	public int getRentFromLease(Tenant tenant) {
		int rent = 0;
		Connection conn = null;
		Statement rentStmt = null;
		
		String rentSQL = "SELECT rent FROM leases WHERE tenant_ID = " + tenant.getTenantIdFromDB();
		
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
	
	public String getStartDateFromLease(Tenant tenant) {
		Date startDate = null;
		
		Connection conn = null;
		Statement startDateStmt = null;
		
		String startDateSQL = "SELECT start_date FROM leases WHERE tenant_ID = " + tenant.getTenantIdFromDB();
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			startDateStmt = conn.createStatement();
			ResultSet startDateRS = startDateStmt.executeQuery(startDateSQL);
			
			while(startDateRS.next()) {
				startDate = startDateRS.getDate(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String startDateStr = String.valueOf(startDate);
		return startDateStr;
	}
	
	public String getEndDateFromLease(Tenant tenant) {
		Date endDate = null;
		
		Connection conn = null;
		Statement endDateStmt = null;
		
		String endDateSQL = "SELECT end_date FROM leases WHERE tenant_ID = " + tenant.getTenantIdFromDB();
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			endDateStmt = conn.createStatement();
			ResultSet endDateRS = endDateStmt.executeQuery(endDateSQL);
			
			while(endDateRS.next()) {
				endDate = endDateRS.getDate(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String endDateStr = String.valueOf(endDate);
		return endDateStr;
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
	
	public String getLandlordNameFromLease(Tenant tenant) {
		String landlordName = null;
		Connection conn = null;
		Statement landlordNameStmt = null;
		
		String landlordNameSQL = "SELECT full_name FROM landlords INNER JOIN leases \r\n"
				+ "ON landlords.landlord_ID = leases.landlord_ID \r\n"
				+ "WHERE leases.tenant_ID = " + tenant.getTenantIdFromDB();
		
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
