package org.duckweedcoll.integration.webdriver

import org.duckweedcoll.WebDriverRoot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import static WebDriverAssistant.*
import static org.junit.Assert.assertNotNull

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
class Circle_wd_ITCase extends WebDriverRoot {

    def username = createRandomUserName()
    def circleName = "name of circle ${Math.random()}"
    def circleDesc = "desc of circle ${Math.random()}"


    @Test
    public void shouldHaveNewCircleButton() {
        assertNotNull "should find a place to create a circle", findElement(driver, 'newcircle')
    }


    @Test
    public void shouldHaveShowCircleButton() {
        findAndClickButton(driver, 'showcircles')
        assertSourceContains(driver, 'Show Circles')
    }


    @Test
    public void createCirclePageShouldHaveAName() {
        gotoCircle(driver)
        findElement(driver, 'name')
        findElement(driver, 'description')
    }


    @Test
    public void newCircleButtonShouldShow_title() {
        gotoCircle(driver)
        assertSourceContains(driver, 'Create A Circle')
    }


    @Test
    public void canCreateCircleAndFindItInShowCircles() {
        checkCreateCircleAndFindItInShowCircles()
    }

    @Test
    public void canOpenCreatedCircle() {
        checkCreateCircleAndFindItInShowCircles()
        findTagWithNameAndTextValue(driver, 'circle-name', circleName).click()
        assertNotNull 'cant find title', findInputWithNameAndTextValue(driver, 'name', circleName)
    }

    @Test
    public void createdCircleShouldHaveSecretary() {
        createCircleWithWeb()
        findAndClickButton(driver, 'profile')
        enterText(driver, 'username', 'something')
        submit(driver)
        findAndClickButton(driver, 'newcircle')
        assertSourceContains(driver, 'the circle should have an secretary', "secretary is: something")
    }


    @Before
    public void before() {
        driver.get("http://localhost:8080");
        logout(driver)
        findAndClickButton(driver, 'login')
        acceptGoogleAuth(driver, username)
    }

    @After
    public void after() {
    }

    private checkCreateCircleAndFindItInShowCircles() {
        createCircleWithWeb()
        findAndClickButton(driver, 'showcircles')
        assertTagWithTextExists(driver, 'circle-name', circleName)
        assertTagWithTextExists(driver, 'circle-description', circleDesc)
    }

    private createCircleWithWeb() {
        gotoCircle(driver)
        enterText(driver, 'name', circleName)
        enterText(driver, 'description', circleDesc)
        submit(driver)
    }

    private WebElement findInputWithNameAndTextValue(WebDriver driver, String tagName, String expectedText) {
        String xpath = "//input[@name='${tagName}'][contains(@value, '${expectedText}')]"
        WebElement ret = findXPathElement(xpath)
        return ret
    }

    private WebElement findTagWithNameAndTextValue(WebDriver driver, String tagName, String expectedText) {
        WebElement ret = findXPathElement("//td[@name='$tagName']//*[contains(text(), '${expectedText}')]")
        return ret
    }

    private WebElement findXPathElement(String xpath) {
        return driver.findElement(By.xpath(xpath))
    }
}
