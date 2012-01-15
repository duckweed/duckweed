package org.duckweedcoll;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.duckweedcoll.WebDriverAssistant.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Sum_wb_ITCase extends WebDriverRoot {

    @Test
    public void test_whenDigitsAreEntered_thenSummedCorrectly() {
        checkSumming("simple sum", driver, "1", "2", "3");
        checkSumming("same numbers", driver, "2", "2", "4");
        checkSumming("pos and neg", driver, "-2", "2", "0");
        checkSumming("neg and pos", driver, "2", "-2", "0");
        checkSumming("unneeded prefix zeros", driver, "02", "02", "4");
        checkSumming("double digits", driver, "17", "19", "36");
        checkSumming("preceding space in 'A'", driver, " 17", "19", "36");
        checkSumming("preceding space in 'B'", driver, "17", " 19", "36");
        checkSumming("preceding space in both", driver, " 17", " 19", "36");
    }

    @Test
    public void test_whenNonNumericIsEntered_thenThrowException() {
        driver.findElement(By.name("a")).sendKeys("non numeric");
        driver.findElement(By.name("Send")).submit();

        assertSourceContains(driver, "should throw a numeric exception", "NumberFormatException");
    }

    @Test
    public void test_structure() {
        assertEquals("Sum Me", getCssSelectorText(driver, "h1"));
    }


    @Before
    public void before() {
        driver.get("http://localhost:8080/sum.groovy");
    }
}
