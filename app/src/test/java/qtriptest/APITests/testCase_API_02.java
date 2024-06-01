package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
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

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
public class testCase_API_02 {
    @Test(groups= {"API Tests"})
    public void testCase02(){
    RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
    RestAssured.basePath = "/api/v1/cities";
    JSONObject obj = new JSONObject();
    
    Response resp = RestAssured.given().queryParam("q", "beng").log().all().when().get();
    Assert.assertEquals(resp.getStatusCode(), 200);

    JsonPath jp = new JsonPath(resp.body().asString());
    System.out.println(jp.getString("[0].id"));
    List<JSONObject> list = jp.getList("$");
    
    Assert.assertEquals(list.size(),1);

    File schemaFile =  new File("/home/crio-user/workspace/nagmakazi8652-ME_API_TESTING_PROJECT/app/src/test/resources/schema.json");
    //app/src/test/resources/schema.json
    // JsonSchemaValidator matcher = JsonSchemaValidator.matchesJsonSchema(schemaFile);
    ///home/crio-user/workspace/nagmakazi8652-ME_API_TESTING_PROJECT/app/src/test/resources/schema.json

    resp.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    System.out.println("resp body"+resp.body().asString());
    System.out.println(jp.getString("[0].description"));

    Assert.assertEquals(jp.getString("[0].description"), "100+ Places");
    // RestAssured.given().log().all().when().get().then().log().all().assertThat().body(matcher);
    // Assert.assertEquals(list[2],"100+ Places");

    // RestAssured.given().log().all().when().get().then().statusCode(200).and().assertThat().body("description",equalTo("100+ Places")).
    //  assertThat().header("Content-Type", "application/json");

    }



}
