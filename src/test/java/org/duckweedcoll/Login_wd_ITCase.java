package org.duckweedcoll;


import org.junit.Before;
import org.junit.Test;

import static org.duckweedcoll.WebDriverAssistant.assertCssSelectorContains;
import static org.duckweedcoll.WebDriverAssistant.assertSourceContains;
import static org.duckweedcoll.WebDriverAssistant.findElement;

public class Login_wd_ITCase extends WebDriverRoot {

    @Test
    public void test_pageWillAcceptUserNameAndPassword() {
        assertSourceContains(driver, "should have login message", "Log In Here");
        assertCssSelectorContains("should have correct title", driver, "h1", "Log In Here");

        findElement(driver, "username");
        findElement(driver, "password");
        findElement(driver, "submit");
    }

    @Test
    public void test_userNameAndPassword_willLogin() {
        findElement(driver, "username").sendKeys("andrew");
        findElement(driver, "password").sendKeys("password");
        findElement(driver, "submit").submit();
        assertSourceContains(driver, "should have login message", "Welcome, andrew");
    }


    @Before
    public void before() {
        driver.get("http://localhost:8080/login.groovy");
    }


}
