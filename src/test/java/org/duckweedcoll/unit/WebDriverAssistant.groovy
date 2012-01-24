package org.duckweedcoll.unit;
/*
      Licensed to the Apache Software Foundation (ASF) under one
      or more contributor license agreements.  See the NOTICE file
      distributed with this work for additional information
      regarding copyright ownership.  The ASF licenses this file
      to you under the Apache License, Version 2.0 (the
      "License"); you may not use this file except in compliance
      with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing,
      software distributed under the License is distributed on an
      "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
      KIND, either express or implied.  See the License for the
      specific language governing permissions and limitations
      under the License.
*/

import org.junit.Assert
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import static org.junit.Assert.assertEquals

public class WebDriverAssistant {
    public static void assertSourceContains(WebDriver driver, String message, String expectedString) {
        Assert.assertTrue(message, driver.getPageSource().contains(expectedString));
    }

    public static void assertSourceContains(WebDriver driver, String expectedString) {
        assertSourceContains(driver, '', expectedString)
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

    public static void findAndClickButton(WebDriver driver, name) {
        driver.findElement(By.name(name)).click()
    }

    static assertFieldContainsValue(WebDriver driver, String field, String value) {
        WebElement element = findElement(driver, field)
        def text = ''
        text = amIaTextArea(element, text)
        text = amIanInputField(text, element)
        assertEquals("should find '$value' in field '$field'", value, text)
    }

    private static String amIanInputField(String text, WebElement element) {
        if (text == '') {
            try {
                text = element.getAttribute('value')
            } catch (Exception e) {

            }
        }
        return text
    }

    private static String amIaTextArea(WebElement element, String text) {
        try {
            text = element.text
        } catch (Exception e) {
        }
        return text
    }
}