package org.duckweedcoll.integration

import org.junit.Before
import org.junit.Test
import static org.duckweedcoll.unit.WebDriverAssistant.findAndClickButton
import org.duckweedcoll.WebDriverRoot
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
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

class Login_wd_ITCase extends WebDriverRoot {
    @Test
    public void shouldLoginAndLogout() {
        findAndClickButton(driver, 'log in')
        accceptGoogleAuth()
        driver.getPageSource().contains('Welcome test@example.com')
        findAndClickButton(driver, 'log out')
        findAndClickButton(driver, 'log out')
    }

    @Test
    public void ifUserHasntBeenSeen_showPlaceToEnterUserName() {
        findAndClickButton(driver, 'log in')
        accceptGoogleAuth()
        sleep 1000
        assertNotNull "should find a place to enter their name", driver.findElement(By.name('username'))
    }


    @Before
    public void before() {
        driver.get("http://localhost:8080");
    }

    private accceptGoogleAuth() {
        List<WebElement> elements = driver.findElements(By.cssSelector('input'))
        WebElement button = elements.find {
            it.getAttribute('value').trim() == 'Log In'
        }
        button.click()
    }

}
