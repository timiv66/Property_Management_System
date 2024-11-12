package Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Requests {
	final String DB_URL = "jdbc:mysql://localhost:3306/propertysystem";
	final String DB_USER = "root";  // Use your MySQL username
	final String DB_PASSWORD = "Tobi4timi$";  // Use your MySQL password
	
	String requestID, requestDescrip, apartName, tenantID, landlordID, status, dateCreated;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getRequestDescrip() {
		return requestDescrip;
	}

	public void setRequestDescrip(String requestDescrip) {
		this.requestDescrip = requestDescrip;
	}

	public String getApartName() {
		return apartName;
	}

	public void setApartName(String apartName) {
		this.apartName = apartName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public void addRequestToDB(Tenant tenant, Lease lease, String location, String description ,String category, String date) {
		String requestId = generateRequestID();
		int tenantId = tenant.getTenantIdFromDB();
		int landlordId = lease.getLandlordIDFromLease(tenant);
		String apartName = lease.getApartNameFromLease(tenant);
		
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
			requestPstmt.executeUpdate();
			
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
