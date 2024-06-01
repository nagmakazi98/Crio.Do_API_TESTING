package qtriptest.APITests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class testCase_API_04 {
    @Test(groups= {"API Tests"})
    public void testCase04(){

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/register";

        JSONObject obj = new JSONObject();

        String email = "deepa" + UUID.randomUUID() + "@gmail.com";
        String password = UUID.randomUUID().toString();
        String confirmpassword = password;

         // Store the last generated email and password
         String lastGeneratedEmail = email;
         String lastGeneratedPassword = password;

        obj.put("email",email);
        obj.put("password",password);
        obj.put("confirmpassword",confirmpassword);

        Response resp = RestAssured.given().contentType("application/json").
        body(obj.toString()).log().all().when().post();
        
        Assert.assertEquals(resp.getStatusCode(), 201);

        JSONObject obj1 = new JSONObject();
        obj1.put("email",lastGeneratedEmail);
        obj1.put("password",lastGeneratedPassword);
        obj1.put("confirmpassword",lastGeneratedPassword);

        Response resp1 = RestAssured.given().contentType("application/json").
        body(obj1.toString()).log().all().when().post();
    
        // Assertion for duplicate user registration
        Assert.assertEquals(resp1.getStatusCode(), 400, "Duplicate user registration succeeded");
        Assert.assertTrue(resp1.getBody().asString().contains("Email already exists"),
                "Error message doesn't contain 'Email already exists'");
    }
    }

  

