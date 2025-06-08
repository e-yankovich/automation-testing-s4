package dev.eyankovich.api.jsonplaceholder;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class CommentsApiTest {
    private static final String API_URI = "https://jsonplaceholder.typicode.com";
    private static final Logger logger = LoggerFactory.getLogger(CommentsApiTest.class);

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = API_URI;
        logger.info("Base URI set to {}", API_URI);

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test(description = "[positive] Get existing comment by id (id = 1)")
    public void getCommentById() {
        logger.info("Sending GET /comments/1");

        Response response = RestAssured.given()
                .when()
                .get("/comments/1");

        Assert.assertEquals(response.statusCode(), 200, "Expected status 200");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Expected JSON content type");

        response.then()
                .body("id", equalTo(1))
                .body("postId", notNullValue())
                .body("email", containsString("@"))
                .body("body", not(emptyString()));

        logger.info("Validated response for comment id = 1");
    }

    @Test(description = "[positive] Get comments by existing postId (postId = 1")
    public void getCommentsByPostId() {
        logger.info("Sending GET /comments?postId=1");

        Response response = RestAssured.given()
                .queryParam("postId", 1)
                .when()
                .get("/comments");

        Assert.assertEquals(response.statusCode(), 200, "Expected status 200");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Expected JSON content type");

        response.then()
                .body("$", not(empty()))
                .body("postId", everyItem(equalTo(1)))
                .body("email", everyItem(containsString("@")));

        logger.info("Validated filtered comments by postId = 1");
    }

    @Test(description = "[negative] Get non-existent comment by id (id = 1000000)")
    public void getNonExistentCommentById() {
        logger.info("Sending GET /comments/1000000");

        Response response = RestAssured.given()
                .when()
                .get("/comments/1000000");

        Assert.assertEquals(response.statusCode(), 404, "Expected status 404 for getting comment with non-exiting id");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Expected JSON content type");

        response.then()
                .body("$", anEmptyMap());

        logger.info("Verified 404 for non-existent comment id");
    }

    @Test(description = "[negative] Get comment with invalid id format (id = abc)")
    public void getCommentWithInvalidId() {
        logger.info("Sending GET /comments/abc");
        Response response = RestAssured.given()
                .when()
                .get("/comments/abc");

        Assert.assertEquals(response.statusCode(), 404, "Expected status 404 for invalid id format");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Expected JSON content type");

        response.then()
                .body("$", anEmptyMap());

        logger.info("Verified 404 for invalid id format");
    }
}