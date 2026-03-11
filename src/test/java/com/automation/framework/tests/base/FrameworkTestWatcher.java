package com.automation.framework.tests.base;

import com.automation.framework.reporting.AllureAttachments;
import com.automation.framework.ui.playwright.PlaywrightManager;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class FrameworkTestWatcher implements TestWatcher, TestExecutionExceptionHandler {
    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(FrameworkTestWatcher.class);
    private static final String FAILURE_CAPTURED = "failureCaptured";

    @Override
    public void testSuccessful(ExtensionContext context) {
        Allure.label("result", "passed");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        captureFailureArtifacts(context, cause);
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        captureFailureArtifacts(context, throwable);
        throw throwable;
    }

    private void captureFailureArtifacts(ExtensionContext context, Throwable cause) {
        if (Boolean.TRUE.equals(context.getStore(NAMESPACE).get(FAILURE_CAPTURED, Boolean.class))) {
            return;
        }

        Optional<Page> currentPage = Optional.ofNullable(safelyGetPage());
        currentPage.ifPresent(page -> {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            AllureAttachments.attachScreenshot("Failure screenshot", screenshot);
            AllureAttachments.attachPageSnapshot("Failure DOM", page);
        });
        AllureAttachments.attachText("Failure reason", cause.getMessage() == null ? cause.toString() : cause.getMessage());
        context.getStore(NAMESPACE).put(FAILURE_CAPTURED, true);
    }

    private Page safelyGetPage() {
        try {
            return PlaywrightManager.page();
        } catch (Exception ignored) {
            return null;
        }
    }
}
