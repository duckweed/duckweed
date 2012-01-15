package org.duckweedcoll;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WebDriverAssistant {
    public static void assertSourceContains(WebDriver driver, String message, String expectedString) {
        Assert.assertTrue(message, driver.getPageSource().contains(expectedString));
    }

    public static void checkSumming(String message, WebDriver driver, String valueA, String valueB, String expectedValue) {
        driver.findElement(By.name("a")).sendKeys(valueA);
        driver.findElement(By.name("b")).sendKeys(valueB);
        driver.findElement(By.name("b")).submit();

        assertSourceContains(driver, message, "Sum is " + expectedValue);
    }

    public static String getCssSelectorText(WebDriver driver, String tag) {
        return driver.findElement(By.cssSelector(tag)).getText();
    }

    public static void assertCssSelectorContains(String message, WebDriver driver, String tag, String expected) {
        assertEquals(message, expected, getCssSelectorText(driver, tag));
    }

    public static WebElement findElement(WebDriver driver, String tag) {
        return driver.findElement(By.name(tag));
    }
}