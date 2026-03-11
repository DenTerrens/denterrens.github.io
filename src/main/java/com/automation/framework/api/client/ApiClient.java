package com.automation.framework.api.client;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.qameta.allure.restassured.AllureRestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);
    private final RequestSpecification specification;

    public ApiClient(String baseUri, Map<String, String> headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addFilter(new AllureRestAssured())
                .setContentType("application/json");
        headers.forEach(builder::addHeader);
        this.specification = builder.build();
    }

    public Response get(String path) {
        LOGGER.info("Sending GET request to {}", path);
        return RestAssured.given(specification).get(path);
    }

    public Response post(String path, Object body) {
        LOGGER.info("Sending POST request to {}", path);
        return RestAssured.given(specification).body(body).post(path);
    }
}
