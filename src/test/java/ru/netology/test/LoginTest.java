package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void deleteData() {
        DataHelper.clearAllData();
    }

    @Test
    void shouldSendValidLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getValidUser();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getValidVerificationCode();
        var dashBoardPage = verificationPage.validVerify(verifyInfo);
        dashBoardPage.dashboardVisible();
    }

}