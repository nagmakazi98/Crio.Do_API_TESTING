package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.UUID;



public class testCase_API_01 {

     @Test(groups= {"API Tests"})
    public void testCase01(){

        // RequestSpecification http = RestAssured.given();    // RequestSpecificatio is a interface
        // http.baseUri("https://qtripdynamic-qa-frontend.vercel.app");
        // http.basePath("/pages/register/");
        // Response resp = RestAssured.when().get();

        // System.out.println(resp.contentType());
        // System.out.println(resp.getStatusCode());
        // System.out.println(resp.getStatusLine());
        // System.out.println(resp.body().asPrettyString());

        // String actual = resp.contentType().toString();
        // System.out.println(actual);

        // Assert.assertEquals(actual, "text/html");
       
        // Register API call
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/register";

        JSONObject obj = new JSONObject();

        String email = "deepa" + UUID.randomUUID() + "@gmail.com";
        String password = UUID.randomUUID().toString();
        String confirmpassword = password;

        obj.put("email",email);
        obj.put("password",password);
        obj.put("confirmpassword",confirmpassword);

        Response resp = RestAssured.given().contentType("application/json").
        body(obj.toString()).log().all().when().post();
        
        Assert.assertEquals(resp.getStatusCode(), 201);

        // Login API call
        RestAssured.basePath = "/api/v1/login";
        obj.remove("confirmpassword");
        resp = RestAssured.given().contentType("application/json").body(obj.toString()).log().all().when().post();

        Assert.assertEquals(resp.getStatusCode(), 201);
        JsonPath jp = new JsonPath(resp.body().asString());

        Assert.assertTrue(jp.getBoolean("success"));
        Assert.assertNotNull(jp.get("data.token"));
        Assert.assertNotNull(jp.get("data.id"));
    }
   
}
   

