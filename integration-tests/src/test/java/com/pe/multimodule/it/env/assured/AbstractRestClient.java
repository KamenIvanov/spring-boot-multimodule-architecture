package com.pe.multimodule.it.env.assured;

import com.pe.multimodule.api.JsonDeserializer;
import com.pe.multimodule.dto.AbstractDto;
import com.pe.multimodule.dto.ErrorCodeDto;
import com.pe.multimodule.dto.ResponseDto;
import com.pe.multimodule.it.HeadersProvider;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AbstractRestClient {

    protected final HeadersProvider headerUtils;
    protected final String                  endpointUrl;

    protected AbstractRestClient(HeadersProvider headerUtils, String endpointUrl) {
        this.headerUtils = headerUtils;
        this.endpointUrl = endpointUrl;
    }

    /* POST */
    protected Response postForSuccess(String path, Map<String, ?> headers, AbstractDto body) {
        return RestAssuredUtils.firePost(endpointUrl + path, headers, body, HttpStatus.SC_OK);
    }

    protected List<String> postForBadRequest(String path, Map<String, ?> headers, AbstractDto body) {
        final var response = RestAssuredUtils.firePost(endpointUrl + path, headers, body, HttpStatus.SC_BAD_REQUEST);

        final ResponseDto error = getResponseDto(response);
        return assertMessages(error, ErrorCodeDto.BAD_REQUEST);
    }

    protected List<String> postForConflict(String path, Map<String, ?> headers, AbstractDto body) {
        final var response = RestAssuredUtils.firePost(endpointUrl + path, headers, body, HttpStatus.SC_CONFLICT);

        final ResponseDto error = getResponseDto(response);
        return assertMessages(error, ErrorCodeDto.CONFLICT);
    }

    protected List<String> postForUnauthorized(String path, Map<String, ?> headers, AbstractDto body) {
        final var response = RestAssuredUtils.firePost(endpointUrl + path, headers, body, HttpStatus.SC_UNAUTHORIZED);

        final ResponseDto error = getResponseDto(response);
        return assertMessages(error, ErrorCodeDto.UNAUTHORIZED);
    }

    /* PUT */
    protected Response putForSuccess(String path, Map<String, ?> headers, AbstractDto body) {
        return RestAssuredUtils.firePut(endpointUrl + path, headers, body, HttpStatus.SC_OK);
    }

    protected List<String> putForNotFound(String path, Map<String, ?> headers, AbstractDto body) {
        final var response = RestAssuredUtils.firePut(endpointUrl + path, headers, body, HttpStatus.SC_NOT_FOUND);

        final ResponseDto error = getResponseDto(response);
        return assertMessages(error, ErrorCodeDto.NOT_FOUND);
    }

    protected List<String> putForUnauthorized(String path, Map<String, ?> headers, AbstractDto body) {
        final var response = RestAssuredUtils.firePut(endpointUrl + path, headers, body, HttpStatus.SC_UNAUTHORIZED);

        final ResponseDto error = getResponseDto(response);
        return assertMessages(error, ErrorCodeDto.UNAUTHORIZED);
    }

    /* GET */
    protected Response getForSuccess(String path, Map<String, ?> headers) {
        return RestAssuredUtils.fireGet(endpointUrl + path, headers, HttpStatus.SC_OK);
    }

    protected List<String> getForUnauthorized(String path, Map<String, ?> headers) {
        final var response = RestAssuredUtils.fireGet(endpointUrl + path, headers, HttpStatus.SC_UNAUTHORIZED);
        final ResponseDto error = getResponseDto(response);
        return assertMessages(error, ErrorCodeDto.UNAUTHORIZED);
    }

    protected List<String> getForNotFound(String path, Map<String, ?> headers) {
        final var response = RestAssuredUtils.fireGet(endpointUrl + path, headers, HttpStatus.SC_NOT_FOUND);
        final ResponseDto error = getResponseDto(response);
        return assertMessages(error, ErrorCodeDto.NOT_FOUND);
    }

    /* DELETE */
    protected Response deleteForSuccess(String path, Map<String, ?> headers) {
        return RestAssuredUtils.fireDelete(endpointUrl + path, headers, HttpStatus.SC_OK);
    }

    protected List<String> deleteForUnauthorized(String path, Map<String, ?> headers) {
        final var response = RestAssuredUtils.fireDelete(endpointUrl + path, headers, HttpStatus.SC_UNAUTHORIZED);
        final ResponseDto error = getResponseDto(response);
        return assertMessages(error, ErrorCodeDto.UNAUTHORIZED);
    }

    protected Map<String, ?> getHttpHeaders() {
        return headerUtils.get();
    }

    protected <T extends AbstractDto> T getResponseDto(Response response) {
        return JsonDeserializer.instance.process(response.getBody().asString());
    }

    protected List<String> assertMessages(ResponseDto error, ErrorCodeDto expectedCode) {
        if (error == null) {
            return Collections.emptyList();
        }

        Assertions.assertEquals(expectedCode, error.getCode());
        return error.getMessages();
    }
}
