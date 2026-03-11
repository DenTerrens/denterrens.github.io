package com.automation.framework.tests.api;

import com.automation.framework.tests.base.BaseApiTest;
import com.automation.framework.utils.ResourceHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("API Smoke Verification")
@Tag("api")
@Tag("smoke")
class UsersApiTest extends BaseApiTest {
    @Test
    @DisplayName("Verify single user details are returned from the public API")
    void getSingleUserReturnsExpectedPayload() {
        Response response = usersApi.getUser(1);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("email")).isEqualTo("Sincere@april.biz");
    }

    @Test
    @DisplayName("Verify user creation returns a created response with the submitted name")
    void createUserReturnsCreatedResponse() {
        Response response = usersApi.createUser(ResourceHelper.readClasspathFile("data/api/create-user.json"));

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.jsonPath().getString("name")).isEqualTo("morpheus");
    }
}
