package org.duckweedcoll.util;
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

import org.apache.commons.lang.RandomStringUtils
import org.junit.Assert
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import static org.duckweedcoll.CircleHandler.NEW_CIRCLE_TAG
import static org.junit.Assert.*

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
            }
            catch (Exception e) {

            }
        }
        return text
    }

    private static String amIaTextArea(WebElement element, String text) {
        try {
            text = element.text
        }
        catch (Exception e) {
        }
        return text
    }

    static logout(def driver) {
        try {
            findAndClickButton(driver, 'logout')
        }
        catch (Exception e) {
            // swallow this
        }
    }

    static acceptGoogleAuth(def driver, def username) {
        try {
            WebElement email = driver.findElement(By.name('email'))
            email.clear()
            email.sendKeys(username)
            List<WebElement> elements = driver.findElements(By.cssSelector('input'))
            WebElement button = elements.find {
                it.getAttribute('value').trim() == 'Log In'
            }
            sleep 100
            button.click()
        }
        catch (Throwable e) {
            fail('cant authenticate');
        }
    }

    static submit(WebDriver driver) {
        findAndClickButton(driver, 'submit')
    }

    static enterText(WebDriver driver, String fieldUnderTest, String expectedText) {
        def ele = findElement(driver, fieldUnderTest)
        Assert.assertNotNull("couldnt find $fieldUnderTest", ele)
        ele.clear()
        ele.sendKeys(expectedText)
    }

    static String createRandomUserName() {
        return oneRandomUpperCaseLetter() + lowerCaseNameOfLength(4) + ' ' + oneRandomUpperCaseLetter() + lowerCaseNameOfLength(9)
    }

    static String oneRandomUpperCaseLetter() {
        return RandomStringUtils.random(1, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')
    }

    static String lowerCaseNameOfLength(int length) {
        return RandomStringUtils.random(length, true, false).toLowerCase()
    }

    static gotoCircle(WebDriver driver) {
        findAndClickButton(driver, NEW_CIRCLE_TAG)
    }

    static assertTagWithTextExists(def driver, String tagName, def expectedValue) {
        List<WebElement> elements = driver.findElements(By.name(tagName))

        assertFalse("can't find any elements with name '$tagName', while looking for '$expectedValue'", elements.isEmpty())
        assertNotNull "cant't find '$tagName' with text '$expectedValue'", elements.find {
            def tagEqualsText = it.text.trim() == expectedValue.trim()
            tagEqualsText
        }
    }
}