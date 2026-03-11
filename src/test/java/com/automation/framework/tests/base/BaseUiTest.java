package com.automation.framework.tests.base;

import com.automation.framework.ui.playwright.PlaywrightManager;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FrameworkTestWatcher.class)
public abstract class BaseUiTest {
    protected Page page;

    @BeforeEach
    void setUpUiSession() {
        PlaywrightManager.start();
        page = PlaywrightManager.page();
    }

    @AfterEach
    void tearDownUiSession() {
        PlaywrightManager.stop();
    }
}
