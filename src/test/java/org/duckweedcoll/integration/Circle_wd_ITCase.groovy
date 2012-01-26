package org.duckweedcoll.integration

import org.duckweedcoll.WebDriverRoot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import static org.duckweedcoll.util.WebDriverAssistant.acceptGoogleAuth
import static org.duckweedcoll.util.WebDriverAssistant.assertSourceContains
import static org.duckweedcoll.util.WebDriverAssistant.createRandomUserName
import static org.duckweedcoll.util.WebDriverAssistant.enterText
import static org.duckweedcoll.util.WebDriverAssistant.findAndClickButton
import static org.duckweedcoll.util.WebDriverAssistant.findElement
import static org.duckweedcoll.util.WebDriverAssistant.logout
import static org.duckweedcoll.util.WebDriverAssistant.submit
import static org.junit.Assert.assertNotNull
import org.openqa.selenium.WebElement
import static org.junit.Assert.assertFalse

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
        assertSourceContains(driver, 'Create A Circle')
    }


    @Test
    public void canCreateCircleAndFindItInShowCircles() {
        findAndClickButton(driver, 'newcircle')
        def expectedCircleName = "name of circle ${Math.random()}"
        def expectedCircleDesc = "desc of circle ${Math.random()}"
        enterText(driver, 'name', expectedCircleName)
        enterText(driver, 'description', expectedCircleDesc)
        submit(driver)

//        TODO: should return to home page
        driver.get("http://localhost:8080");
        findAndClickButton(driver, 'showcircles')

        assertTagWithTextExists('circle-name', expectedCircleName)
        assertTagWithTextExists('circle-description', expectedCircleDesc)
    }


    private assertTagWithTextExists(String tagName, expectedValue) {
        List<WebElement> elements = driver.findElements(By.name(tagName))

        assertFalse("can't find any elements with name '$tagName', while looking for '$expectedValue'", elements.isEmpty())
        assertNotNull "cant't find '$tagName' with text '$expectedValue'", elements.find {
            def tagEqualsText = it.text.trim() == expectedValue.trim()
            tagEqualsText
        }
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

}
