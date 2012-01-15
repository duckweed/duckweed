package org.duckweedcoll;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Sum_wb_ITCase {


    private static WebDriver driver;

    @Test
    public void test_whenDigitsAreEntered_thenSummedCorrectly() {
        checkSumming("simple sum", "1", "2", "3");
        checkSumming("same numbers", "2", "2", "4");
        checkSumming("pos and neg", "-2", "2", "0");
        checkSumming("neg and pos", "2", "-2", "0");
        checkSumming("unneeded prefix zeros", "02", "02", "4");
        checkSumming("double digits", "17", "19", "36");
        checkSumming("preceding space in 'A'", " 17", "19", "36");
        checkSumming("preceding space in 'B'", "17", " 19", "36");
        checkSumming("preceding space in both", " 17", " 19", "36");
    }

    @Test
    public void test_whenNonNumericIsEntered_thenThrowException() {
        driver.findElement(By.name("a")).sendKeys("non numeric");
        driver.findElement(By.name("Send")).submit();

        assertSourceContains("should throw a numeric exception", "NumberFormatException");
    }

    @Test
    public void test_structure() {
        assertEquals("Sum Me", getCssSelectorText("h1"));
    }

    private String getCssSelectorText(String tag) {
        return driver.findElement(By.cssSelector(tag)).getText();
    }


    private void checkSumming(String message, String valueA, String valueB, String expectedValue) {
        driver.findElement(By.name("a")).sendKeys(valueA);
        driver.findElement(By.name("b")).sendKeys(valueB);
        driver.findElement(By.name("b")).submit();

        assertSourceContains(message, "Sum is " + expectedValue);
    }

    private void assertSourceContains(String message, String expectedString) {
        assertTrue(message, driver.getPageSource().contains(expectedString));
    }

    @AfterClass
    public static void after() {
        driver.close();
    }

    @BeforeClass
    public static void beforeClass() {
        driver = new FirefoxDriver();
    }

    @Before
    public void before() {
        driver.get("http://localhost:8080/sum.groovy");
    }
}
