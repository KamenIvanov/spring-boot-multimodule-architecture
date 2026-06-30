package com.pe.multimodule.it.env.assured;

import com.pe.multimodule.dto.AbstractDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class RestAssuredUtils {

    private RestAssuredUtils() {
        // Singleton
    }

    public static Response fireGet(String url, Map<String, ?> headers, int expectedStatus) {
        final var response = RestAssured
                .given()
                .headers(headers)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get(url);

        assertResponse(response, expectedStatus);
        return response;
    }

    public static Response firePost(String url, Map<String, ?> headers, AbstractDto body, int expectedStatus) {
        final var requestSpec = RestAssured
                .given()
                .headers(headers)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
        if (body != null) {
            requestSpec.body(body);
        }

        final var response = requestSpec.post(url);

        assertResponse(response, expectedStatus);
        return response;
    }

    public static Response firePost(String url, Map<String, ?> headers, int expectedStatus) {
        final var response = RestAssured
                .given()
                .headers(headers)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post(url);
        assertResponse(response, expectedStatus);
        return response;
    }

    public static Response firePut(String url, Map<String, ?> headers, AbstractDto body, int expectedStatus) {
        final var requestSpec = RestAssured
                .given()
                .headers(headers)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
        if (body != null) {
            requestSpec.body(body);
        }

        final var response = requestSpec.put(url);

        assertResponse(response, expectedStatus);
        return response;
    }

    public static Response firePut(String url, String body, int expectedStatus) {
        final var requestSpec = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .accept(ContentType.JSON);
        if (body != null) {
            requestSpec.body(body);
        }

        final var response = requestSpec.put(url);

        assertResponse(response, expectedStatus);
        return response;
    }

    public static Response fireDelete(String url, Map<String, ?> headers, int expectedStatus) {
        final var response = RestAssured
                .given()
                .headers(headers)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .delete(url);

        assertResponse(response, expectedStatus);
        return response;
    }

    private static void assertResponse(Response response, int expectedStatus) {
        response.then()
                .log()
                .body()
                .statusCode(expectedStatus);

        if (!response.body().asString().isEmpty()) {
            response.then().assertThat().contentType(ContentType.JSON);
        }
    }
}
