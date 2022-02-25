package org.comcast.lcs;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Integration Tests
 * In a traditional project, there would be unit test coverage.
 *
 * @author WHITEHEADN
 */
@QuarkusTest
public class LCSResourceTest {

    @Test
    public void testValidRequestSingleResult() {
        given()
            .contentType(ContentType.JSON)
            .body(LCSResourceTest.class.getClassLoader().getResourceAsStream("valid.json"))
        .when()
            .post("/lcs")
        .then()
            .statusCode(200)
            .body("lcs", Matchers.hasSize(1))
            .body("lcs.value", Matchers.contains("cast"));
    }

    @Test
    public void testValidRequestMultipleResults() {
        given()
            .contentType(ContentType.JSON)
            .body(LCSResourceTest.class.getClassLoader().getResourceAsStream("valid_multiple.json"))
        .when()
            .post("/lcs")
        .then()
            .statusCode(200)
            .body("lcs", Matchers.hasSize(2))
            .body("lcs.value", Matchers.contains("A", "C"));
    }

    @Test
    public void testValidRequestEmptyString() {
        given()
            .contentType(ContentType.JSON)
            .body(LCSResourceTest.class.getClassLoader().getResourceAsStream("valid_empty_string.json"))
        .when()
            .post("/lcs")
        .then()
            .statusCode(200)
            .body("lcs", Matchers.hasSize(1))
            .body("lcs.value", Matchers.contains(""));
    }

    @Test
    public void testValidRequestSingleValue() {
        given()
            .contentType(ContentType.JSON)
            .body(LCSResourceTest.class.getClassLoader().getResourceAsStream("valid_single_value.json"))
        .when()
            .post("/lcs")
        .then()
            .statusCode(200)
            .body("lcs", Matchers.hasSize(1))
            .body("lcs.value", Matchers.contains("comcast"));
    }

    @Test
    public void testInvalidRequestStringsEmpty() {
        given()
            .contentType(ContentType.JSON)
            .body(LCSResourceTest.class.getClassLoader().getResourceAsStream("invalid_missing.json"))
        .when()
            .post("/lcs")
        .then()
            .statusCode(400)
            .body("message", Matchers.contains("Provided values must not be missing, or null."));
    }

    @Test
    public void testInvalidRequestNoBody() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .post("/lcs")
        .then()
            .statusCode(400)
            .body("message", Matchers.contains("Empty request payload."));
    }

    @Test
    public void testInvalidRequestNonUniqueValues() {
        given()
            .contentType(ContentType.JSON)
            .body(LCSResourceTest.class.getClassLoader().getResourceAsStream("invalid_set.json"))
        .when()
            .post("/lcs")
        .then()
            .statusCode(422)
            .body("message", Matchers.contains("Supplied request must contain a unique set of values."));
    }

    @Test
    public void testInvalidRequestBadFormat() {
        given()
            .contentType(ContentType.JSON)
            .body(LCSResourceTest.class.getClassLoader().getResourceAsStream("invalid_format.json"))
        .when()
            .post("/lcs")
        .then()
            .statusCode(400)
            .body("message", Matchers.contains("At least one value field is null or malformed."));
    }
}