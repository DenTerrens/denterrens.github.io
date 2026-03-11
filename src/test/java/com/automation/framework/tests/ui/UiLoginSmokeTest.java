package com.automation.framework.tests.ui;

import com.automation.framework.ui.pages.InventoryPage;
import com.automation.framework.ui.pages.LoginPage;
import com.automation.framework.tests.base.BaseUiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UI Login Smoke Verification")
@Tag("ui")
@Tag("smoke")
class UiLoginSmokeTest extends BaseUiTest {
    @Test
    @DisplayName("Verify a standard user can sign in and add an item to the cart")
    void standardUserCanLoginAndAddItemToCart() {
        LoginPage loginPage = new LoginPage(page).open();
        InventoryPage inventoryPage = new InventoryPage(page);

        loginPage.loginAs("standard_user", "secret_sauce");
        assertThat(inventoryPage.isLoaded()).isTrue();

        inventoryPage.addBackpackToCart();
        assertThat(inventoryPage.cartBadgeCount()).isEqualTo("1");
    }

    @Test
    @DisplayName("Verify a locked out user sees a friendly error message")
    void lockedOutUserSeesFriendlyError() {
        LoginPage loginPage = new LoginPage(page).open();

        loginPage.loginAs("locked_out_user", "secret_sauce");
        assertThat(loginPage.errorMessage()).contains("Sorry, this user has been locked out");
    }
}
