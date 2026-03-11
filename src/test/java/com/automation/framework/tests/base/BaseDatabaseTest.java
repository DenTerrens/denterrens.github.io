package com.automation.framework.tests.base;

import com.automation.framework.db.DatabaseClient;
import com.automation.framework.utils.SqlScriptRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Path;

public abstract class BaseDatabaseTest {
    protected DatabaseClient databaseClient;

    @BeforeEach
    void setUpDatabase() {
        databaseClient = new DatabaseClient();
        SqlScriptRunner.runScript(databaseClient.connection(), Path.of("src", "test", "resources", "db", "schema.sql"));
        SqlScriptRunner.runScript(databaseClient.connection(), Path.of("src", "test", "resources", "db", "seed.sql"));
    }

    @AfterEach
    void tearDownDatabase() {
        databaseClient.close();
    }
}
