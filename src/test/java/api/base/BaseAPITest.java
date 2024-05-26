package api.base;

import config.ConfigProperties;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseAPITest {
    @BeforeAll
    public static void setup() {
        RestAssured.filters(new RequestLoggingFilter(LogDetail.BODY), new ResponseLoggingFilter(LogDetail.STATUS));
    }

    protected static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigProperties.baseAPIUrl)
                .setContentType(ContentType.JSON)
                .build();
    }

    protected static Response get(String endpoint) {
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
