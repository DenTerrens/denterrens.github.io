package com.automation.framework.ui.pages;

import com.automation.framework.config.ConfigManager;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    public LoginPage(Page page) {
        super(page);
    }

    public LoginPage open() {
        page.navigate(ConfigManager.get("ui.baseUrl") + "/");
        return this;
    }

    public LoginPage loginAs(String username, String password) {
        locator("#user-name").fill(username);
        locator("#password").fill(password);
        locator("#login-button").click();
        return this;
    }

    public String errorMessage() {
        return locator("[data-test='error']").innerText();
    }
}
