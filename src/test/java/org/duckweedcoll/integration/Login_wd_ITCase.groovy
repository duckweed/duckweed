package org.duckweedcoll.integration

import org.duckweedcoll.WebDriverRoot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import static org.duckweedcoll.unit.WebDriverAssistant.*
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.fail

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
class Login_wd_ITCase extends WebDriverRoot {

    @Test
    public void shouldLoginAndLogout() {
        driver.getPageSource().contains('Welcome test@example.com')
        findAndClickButton(driver, 'logout')
        assertNotNull driver.findElement(By.name('login'))
    }

    @Test
    public void shouldHaveProfileButton() {
        assertNotNull "should find a profile button", findElement(driver, 'profile')
        assertSourceContains(driver, 'test@example.com')
    }

    @Test
    public void profileShouldContainANickname() {
        assertProfileFieldSavedAndShown(driver, 'username')
    }

    @Test
    public void profileShouldContainABio() {
        assertProfileFieldSavedAndShown(driver, 'bio')
    }

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
        findAndClickButton(driver, 'newcircle')
        findElement(driver, 'name')
        findElement(driver, 'description')
    }

    @Test
    public void newCircleButtonShouldShow_title() {
        findAndClickButton(driver, 'newcircle')
        sleep 1000
        assertSourceContains(driver, 'Create A Circle')
    }

    @Test
    public void newCircle_shouldSaveAndShowCircle() {
        findAndClickButton(driver, 'newcircle')
        def expectedCircleName = "name of circle ${Math.random()}"
        def expectedCircleDesc = "desc of circle ${Math.random()}"
        enterText(driver, 'name', expectedCircleName)
        enterText(driver, 'description', expectedCircleDesc)
        submit(driver)

//        sleep 1000

        findAndClickButton(driver, 'showcircles')
        assertSourceContains(driver, expectedCircleName)
        assertSourceContains(driver, expectedCircleDesc)
    }

    @Before
    public void before() {
        driver.get("http://localhost:8080");
        logout()
        findAndClickButton(driver, 'login')
        acceptGoogleAuth()
    }

    @After
    public void after() {
    }

    private logout() {
        try {
            findAndClickButton(driver, 'logout')
        } catch (Exception e) {
            // swallow this
        }
    }

    private acceptGoogleAuth() {
        try {
            List<WebElement> elements = driver.findElements(By.cssSelector('input'))
            WebElement button = elements.find {
                it.getAttribute('value').trim() == 'Log In'
            }
            sleep 100
            button.click()
        } catch (Throwable e) {
            fail('cant authenticate');
        }
    }

    private static assertProfileFieldSavedAndShown(WebDriver driver, String field) {
        def expectedText = "some $field"

        findAndClickButton(driver, 'profile');
        enterText(driver, field, expectedText)
        submit(driver)
        assertSourceContains(driver, expectedText)
    }

    private static submit(WebDriver driver) {
        findAndClickButton(driver, 'submit')
    }

    private static enterText(WebDriver driver, String fieldUnderTest, GString expectedText) {
        def ele = findElement(driver, fieldUnderTest)
        assertNotNull("couldnt find $fieldUnderTest", ele)
        ele.clear()
        ele.sendKeys(expectedText)
    }
}
