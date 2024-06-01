package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;

public class testCase_API_03 {
    @Test(groups= {"API Tests"})
    public void testCase03(){
        String token;
        String userID;

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

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/reservations/new";

        token = jp.getString("data.token");
        userID = jp.getString("data.id");
        token = "Bearer " + token; 

        JSONObject obj1 = new JSONObject();
        obj1.put("userId",userID);
        obj1.put("name","testdeepa");
        obj1.put("date","2024-10-03");
        obj1.put("person","3");
        obj1.put("adventure","2447910730");


        Response resp1 = RestAssured.given().contentType("application/json").header("Authorization",token).body(obj1.toString()).log().all().when().post();

        Assert.assertEquals(resp1.getStatusCode(), 200);
        Assert.assertTrue(resp1.body().jsonPath().getBoolean("success"));
}
}