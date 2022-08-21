package API;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class PetStoreTest {

    static Response response;

    @Test
    public void findByStatusAvailable() {

        baseURI = "https://petstore.swagger.io/v2";
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .param("status", "available")
                .when()
                .get("/pet/findByStatus");
        System.out.println(response.getBody().asPrettyString());
        System.out.println(response.getStatusCode());

    }

    @Test
    public void findByStatusPending() {

        baseURI = "https://petstore.swagger.io/v2";
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .param("status", "pending")
                .when()
                .get("/pet/findByStatus");
        System.out.println(response.getBody().asPrettyString());
        System.out.println(response.getStatusCode());
    }

    @Test
    public void findByStatusSold() {

        baseURI = "https://petstore.swagger.io/v2";
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .param("status", "sold")
                .when()
                .get("/pet/findByStatus");
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test
    public void petIdThird() {

        baseURI = "https://petstore.swagger.io/v2";
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .param("status", "available")
                .when()
                .get("/pet/1717");
        System.out.println(response.getBody().asPrettyString());
        System.out.println(response.getStatusCode());
    }

    @Test
    public void petIdPost() {

        baseURI = "https://petstore.swagger.io/v2";
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .param("status", "sold")
                .param("name", "pars")
                .when()
                .post("pet/9223372036854034000");
        System.out.println(response.getBody().asPrettyString());
        System.out.println(response.getStatusCode());
    }

    @Test
    public void petIdDelete() {
        baseURI = "https://petstore.swagger.io/v2";
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .header("api_key", "aliqua velit fugiat tempor adipisicing")
                .delete("/pet/2020");
        System.out.println(response.getBody().asPrettyString());
        System.out.println(response.getStatusCode());

    }

}

