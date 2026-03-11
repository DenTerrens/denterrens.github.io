package com.automation.framework.reporting;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public final class AllureAttachments {
    private AllureAttachments() {
    }

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), ".txt");
    }

    public static void attachHtml(String name, String content) {
        Allure.addAttachment(name, "text/html", new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), ".html");
    }

    public static void attachScreenshot(String name, byte[] screenshotBytes) {
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
    }

    public static void attachPageSnapshot(String name, Page page) {
        attachHtml(name, page.content());
    }
}
