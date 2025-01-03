package Property;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Requests {
	final String DB_URL = "jdbc:mysql://localhost:3306/propertysystem";
	final String DB_USER = "root";  // Use your MySQL username
	final String DB_PASSWORD = "Tobi4timi$";  // Use your MySQL password
	

	public int addRequestToDB(Tenant tenant, Lease lease, String location, String description ,String category, String date) {
		String requestId = generateRequestID();
		int tenantId = tenant.getTenantIdFromDB();
		int landlordId = lease.getLandlordIDFromLease(tenant);
		String apartName = lease.getApartNameFromLease(tenant);
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement requestPstmt = null;
		
		//SQL Commands 
	    String requestSQL = "INSERT INTO requests (request_ID, request_location, request_category, request_description, request_created_date, apartment_name, tenant_ID, landlord_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
	    try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			requestPstmt = conn.prepareStatement(requestSQL);
			requestPstmt.setString(1, requestId);
			requestPstmt.setString(2, location);
			requestPstmt.setString(3, category);
			requestPstmt.setString(4, description);
			requestPstmt.setString(5, date);
			requestPstmt.setString(6, apartName);
			requestPstmt.setInt(7, tenantId);
			requestPstmt.setInt(8, landlordId);
			result = requestPstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return result;
	}
	
	public String getTenantNameFromRequests(String requestId) {
		String tenantName = null;
		
		Connection conn = null;
		Statement tenantNameStmt = null;
		
		String tenantNameSQL = "SELECT tenants.full_name FROM requests INNER JOIN\r\n"
				+ "tenants ON requests.tenant_ID = tenants.tenant_ID\r\n"
				+ "WHERE requests.request_ID = " + requestId;
		
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
	
	public String getApartNameFromRequests(String requestId) {
		String apartName = null;
		
		Connection conn = null;
		Statement apartNameStmt = null;
		
		String apartNameSQL = "SELECT apartment_name FROM requests WHERE request_ID = " + requestId;
		
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
	
	public String getLocationFromRequests(String requestId) {
		String location = null;
		
		Connection conn = null;
		Statement locationStmt = null;
		
		String locationSQL = "SELECT request_location FROM requests WHERE request_ID = " + requestId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			locationStmt = conn.createStatement();
			ResultSet locationRS = locationStmt.executeQuery(locationSQL);
			
			while(locationRS.next()) {
				location = locationRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}
	
	public String getCategoryFromRequests(String requestId) {
		String category = null;
		
		Connection conn = null;
		Statement categoryStmt = null;
		
		String categorySQL = "SELECT request_category FROM requests WHERE request_ID = " + requestId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			categoryStmt = conn.createStatement();
			ResultSet categoryRS = categoryStmt.executeQuery(categorySQL);
			
			while(categoryRS.next()) {
				category = categoryRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return category;
	}
	
	public String getDescriptionFromRequests(String requestId) {
		String description = null;
		
		Connection conn = null;
		Statement descriptionStmt = null;
		
		String descriptionSQL = "SELECT request_description FROM requests WHERE request_ID = " + requestId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			descriptionStmt = conn.createStatement();
			ResultSet descriptionRS = descriptionStmt.executeQuery(descriptionSQL);
			
			while(descriptionRS.next()) {
				description = descriptionRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return description;
	}
	
	public String getStatusFromRequests(String requestId) {
		String status = null;
		
		Connection conn = null;
		Statement statusStmt = null;
		
		String statusSQL = "SELECT request_status FROM requests WHERE request_ID = " + requestId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			statusStmt = conn.createStatement();
			ResultSet statusRS = statusStmt.executeQuery(statusSQL);
			
			while(statusRS.next()) {
				status = statusRS.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public String getStartDateForRequest(String requestId) {
		Date startDate = null;
		
		Connection conn = null;
		Statement startDateStmt = null;
		
		String startDateSQL = "SELECT request_created_date FROM requests WHERE request_ID = " + requestId;
		
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
	
	public void updateStatusForRequest(String requestId, String status) {
		
		Connection conn = null;
		Statement updateStatusStmt = null;

		String updateStatusSQL = "UPDATE requests SET request_status = '" + status + "' WHERE request_ID = " + requestId;
	
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			updateStatusStmt = conn.createStatement();
			updateStatusStmt.execute(updateStatusSQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteRequestFromDB(String requestId) {
		Connection conn = null;
		Statement delRequestStmt = null;
		
		String delRequestSQL = "DELETE FROM requests WHERE request_ID = " + requestId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			delRequestStmt = conn.createStatement();
			delRequestStmt.execute(delRequestSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteAllRequestForTenantFromDB(Tenant tenant) {
		int tenantId = tenant.getTenantIdFromDB();
		Connection conn = null;
		Statement delRequestStmt = null;
		
		String delRequestSQL = "DELETE FROM requests WHERE tenant_ID = " + tenantId;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			delRequestStmt = conn.createStatement();
			delRequestStmt.execute(delRequestSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	public static String generateRequestID() {
        int length = 6;
        StringBuilder randomStringBuilder = new StringBuilder(length);
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            int randomNumber = rand.nextInt(10); 
            randomStringBuilder.append(randomNumber);
        }
        return randomStringBuilder.toString();
    }

}
