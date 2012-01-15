package org.duckweedcoll;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverRoot {

    static WebDriver driver;


    @AfterClass
    public static void afterClass() {
        driver.close();
    }

    @BeforeClass
    public static void beforeClass() {
        driver = new FirefoxDriver();
    }
}
