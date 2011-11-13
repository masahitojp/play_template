import org.junit.*;
import org.junit.After;
import org.junit.Before;

import models.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;

public class SecurityTest extends FunctionalTest {
	
	@Before
	public void startUp(){
		Fixtures.deleteDatabase();
		Fixtures.loadModels("initial-data.yml");
	}
	
	@After
	public void end(){
		Fixtures.deleteDatabase();
		
	}
	
	@Test
	public void testThatIndexdPageNeedsLongin(){
		Response response = GET("/");
		assertStatus(302, response);
		assertLocationRedirect("/login", response);
	}
	
	@Test
	public void testThatUserCanLogin(){
		loginAs("user");
		Response response = GET("/");
		assertContentMatch("Logged in as user", response);
	}
    @Test
    public void testThatUserCannotAccessAdminPage() {
    	loginAs("user");
        Response response = GET("/admin");
        assertStatus(403, response);
    }

    @Test
    public void testThatAdminAccessAdminPage() {
    	loginAs("admin");
    	Response response = GET("/admin");
        assertStatus(200, response);
    }
	private void assertLocationRedirect(String location, Response resp){
		assertHeaderEquals("Location", location , resp);
	}
	private void loginAs(String user){
		Response response = POST("/login?username=" + user + "&password=secret");
		assertStatus(302,response);
		assertLocationRedirect("/", response);
	}
}
