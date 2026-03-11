package com.automation.framework.tests.integration;

import com.automation.framework.api.service.UsersApi;
import com.automation.framework.tests.base.BaseDatabaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("API And Database Verification")
@Tag("integration")
class ApiDatabaseVerificationTest extends BaseDatabaseTest {
    private final UsersApi usersApi = new UsersApi();

    @Test
    @DisplayName("Verify API data can be persisted and verified in the audit table")
    void apiResponseCanBePersistedAndVerifiedInDatabase() {
        String email = usersApi.getUser(1).jsonPath().getString("email");
        databaseClient.update("insert into api_audit (audit_id, source, payload_value) values (?, ?, ?)", 2001, "jsonplaceholder", email);

        List<Map<String, Object>> rows = databaseClient.query("select payload_value from api_audit where audit_id = ?", 2001);
        assertThat(rows).singleElement().extracting(row -> row.get("payload_value")).isEqualTo("Sincere@april.biz");
    }
}
