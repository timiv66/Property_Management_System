package Property;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ApartmentTest {
	Tenant tenant = new Tenant();
	
	@Test
	void test() {
		String email = "pg420@yahoo.com";
		String password="Chris4stewie19!";
		boolean result = tenant.authenticateTenantUser(email, password);
		assertTrue("The tenant should be able to log in", result);
	}

}
