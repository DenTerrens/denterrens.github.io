package com.automation.framework.tests.db;

import com.automation.framework.tests.base.BaseDatabaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Database Verification")
@Tag("db")
class DatabaseVerificationTest extends BaseDatabaseTest {
    @Test
    @DisplayName("Verify active customer records are available in the seeded database")
    void canQuerySeededCustomerData() {
        List<Map<String, Object>> rows = databaseClient.query("select customer_id, email, status from customer_account where status = ?", "ACTIVE");

        assertThat(rows).hasSize(2);
        assertThat(rows).extracting(row -> row.get("email")).contains("standard_user@demo.local", "api_auditor@demo.local");
    }
}
