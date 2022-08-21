package API;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GroceryStoreTest {


    static Response response;

    @Test
    public void allGrocery() {

        baseURI ="https://my.api.mockaroo.com";
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .header("x-api-key","8328c480")
                .get("/allGrocery.json");
        System.out.println(response.getBody().asPrettyString());
        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test
    public void addGrocery() {

        baseURI ="https://my.api.mockaroo.com";
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .header("x-api-key","8328c480")
                .post("/add.json");
        System.out.println(response.getBody().asPrettyString());
        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void allGroceryName() {

        baseURI ="https://my.api.mockaroo.com";
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .header("x-api-key","8328c480")
                .get("/allGrocery/123.json");
        System.out.println(response.getBody().asPrettyString());
        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.statusCode(), 200);
    }

}
