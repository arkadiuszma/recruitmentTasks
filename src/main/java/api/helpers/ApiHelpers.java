package api.helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiHelpers {

    private static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    public static Response get(String endpoint) {
        return given()
                      .spec(requestSpec()).
                when()
                            .get(endpoint).
                then()
                            .statusCode(200)
                            .extract()
                            .response();
    }
}
