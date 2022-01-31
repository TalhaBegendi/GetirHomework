package services;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SampleTest extends BaseClass {

    Response response;
    String token;
    int bookingID;

    @BeforeClass
    public static void url()
    {
        RestAssured.baseURI="https://restful-booker.herokuapp.com/";
    }
    @Test
    public  void test01_postCreateToken() throws IOException {
        JSONObject jsonObject = BaseClass.readJsonFile("createToken");
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonObject.toString())
                .when()
                .post("auth")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        token = response.path("token").toString();
    }
    @Test
    public  void test02_getBookingID() {
        response = RestAssured.given()
                .when()
                .get("booking")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public  void test03_postCreateBooking() throws IOException {
        JSONObject jsonObject = BaseClass.readJsonFile("createBooking");
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonObject.toString())
                .when()
                .post("booking")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        bookingID = response.path("bookingid");
    }
    @Test
    public void test04_putUpdateBooking() throws IOException {
        JSONObject jsonObject = BaseClass.readJsonFile("updateBooking");
        response = RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .header("Cookie", "token="+token)
                .body(jsonObject.toString())
                .pathParam("id",bookingID)
                .when()
                .put("booking/{id}")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @DataProvider(name ="dataProvider")
    public  Object[][] dataProvider() {
    return new Object[][]{
           {bookingID,200},
           {1,200}
    };
    }
    @Test (dataProvider = "dataProvider")
    public  void test05_getBookingDetails(int bookingId1, int statusCode) {
        response = RestAssured.given()
                .contentType("application/json")
                .when()
                .get("booking/"+bookingId1)
                .then()
                .statusCode(statusCode)
                .log().all()
                .extract().response();
    }
    @Test
    public void test06_patchPartialUpdateBooking() throws IOException {
        JSONObject jsonObject = BaseClass.readJsonFile("patchBooking");
        response = RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .header("Cookie", "token="+token)
                .body(jsonObject.toString())
                .pathParam("id",bookingID)
                .when()
                .patch("booking/{id}")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void test07_deleteBooking() throws IOException {
        response = RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .header("Cookie", "token="+token)
                .pathParam("id",bookingID)
                .when()
                .delete("booking/{id}")
                .then()
                .statusCode(201)
                .log().all()
                .extract().response();
        System.out.println(response.getBody().asString()+" Kayıt Silindi");


    }

    @Test
    public void sample() {
        String baseUrl = "https://petstore.swagger.io/v2/store/inventory";
        RequestSpecification restAssuredReq = RestAssured.given()
                .header("Study","Test")
                .log()
                .all(true);
        Response response = restAssuredReq.get(baseUrl);
        attachment(restAssuredReq, baseUrl, response);
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    public String attachment(RequestSpecification httpRequest, String baseUrl, Response response) {
        String html = "Url = " + baseUrl + "\n \n" +
                "Request Headers = " + ((RequestSpecificationImpl) httpRequest).getHeaders() + "\n \n" +
                "Request Body = " + ((RequestSpecificationImpl) httpRequest).getBody() + "\n \n" +
                "Response Body = " + response.getBody().asString();
        Allure.addAttachment("Request Detail", html);
        return html;

    }
    
        @Test
    public void sample() {
        String baseUrl = "https://petstore.swagger.io/v2/store/inventory";
        RequestSpecification restAssuredReq = RestAssured.given()
                .header("Study","Test")
                .log()
                .all(true);
        Response response = restAssuredReq.get(baseUrl);
        attachment(restAssuredReq, baseUrl, response);
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    public String attachment(RequestSpecification httpRequest, String baseUrl, Response response) {
        String html = "Url = " + baseUrl + "\n \n" +
                "Request Headers = " + ((RequestSpecificationImpl) httpRequest).getHeaders() + "\n \n" +
                "Request Body = " + ((RequestSpecificationImpl) httpRequest).getBody() + "\n \n" +
                "Response Body = " + response.getBody().asString();
        Allure.addAttachment("Request Detail", html);
        return html;

    }
    
}
