package org.duckweedcoll.integration

import org.apache.commons.lang.RandomStringUtils
import org.duckweedcoll.WebDriverRoot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import static org.junit.Assert.assertNotNull

import static org.duckweedcoll.util.WebDriverAssistant.*

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

    def username = createRandomUserName()

    @Test
    public void shouldLoginAndLogout() {
        driver.getPageSource().contains('Welcome test@example.com')
        findAndClickButton(driver, 'logout')
        assertNotNull findElement(driver, 'login')
    }

    @Test
    public void shouldBeAnAboutBox() throws Exception{
        assertNotNull 'should be an about box', findElement(driver, 'about')
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
