package org.duckweedcoll.integration

import org.apache.commons.lang.RandomStringUtils
import org.duckweedcoll.WebDriverRoot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import static org.duckweedcoll.util.WebDriverAssistant.*
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
class Profile_wd_ITCase extends WebDriverRoot {

    def username = createRandomUserName()

    @Test
    public void shouldHaveProfileButton() {
        assertNotNull "should find a profile button", findElement(driver, 'profile')
        assertSourceContains(driver, username)
    }

    @Test
    public void profileShouldContainANickname() {
        assertProfileFieldSavedAndShownOnHomePage(driver, 'username')
    }

    @Test
    public void testProfileInfoShouldBeAvailableNextClick() {
        def expectedUsername = 'user name'
        def expectedBio = 'a bit of a bio'

        findAndClickButton(driver, 'profile')
        enterText(driver, 'username', expectedUsername)
        enterText(driver, 'bio', expectedBio)
        submit(driver)
        findAndClickButton(driver, 'profile')

        assertFieldContainsValue(driver, 'username', expectedUsername)
        assertFieldContainsValue(driver, 'bio', expectedBio)
    }

    @Test
    public void profileShouldContainABio() {
        assertProfileFieldSavedAndShownOnHomePage(driver, 'bio')
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

    static assertProfileFieldSavedAndShownOnHomePage(WebDriver driver, String field) {
        def expectedText = "some $field"

        findAndClickButton(driver, 'profile');
        enterText(driver, field, expectedText)
        submit(driver)
        assertSourceContains(driver, expectedText)
    }


}
