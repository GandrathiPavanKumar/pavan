package Day1;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

/*
	given()
		content typr, set cookies, add auth,add param, set headers info etc....
	when()
		get, post, put, delete
	then()
		validate status code, extract response, extract headers cookies and response body....
	
	
	
*/
public class HttpRequest {
	
	int id;
	
	@Test(priority=1)
	void getUsers() {
		when()
			.get("https://reqres.in/api/users?page=2")
		
		.then()
			.statusCode(200)
			.body("page",equalTo(2))
			.log().all();
	}
	
	
	@Test(priority=2)
	void putRequest() {
		HashMap data= new HashMap();
		data.put("name", "pavan");
		data.put("job","automation test engineer");
		
		id = given()
				.contentType("application/json")
				.body(data)
			.when()
				.post("https://reqres.in/api/users")
				.jsonPath().getInt("id");
		System.out.println("ID : ====> "+id);
//			.then()
//				.statusCode(201)
//				.log().all();
	//		
	}
	
	
	
	@Test(priority=3,dependsOnMethods= {"putRequest"})
	void updateRequest() {
		HashMap data= new HashMap();
		data.put("name", "pavan kumar");
		data.put("job","test engineer");
		
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.post("https://reqres.in/api/users"+id)
		.then()
			.statusCode(201)
			.log().all();		
	}
	
	
	@Test(priority=4)
	void deleteRequest() {
		when()
			.delete("https://reqres.in/api/users/"+id)
		.then()
			.statusCode(204)
			.log().all();
	}
	
}
