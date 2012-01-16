package org.duckweedcoll;

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

import org.junit.Before;
import org.junit.Test;

import static org.duckweedcoll.WebDriverAssistant.assertCssSelectorContains;
import static org.duckweedcoll.WebDriverAssistant.assertSourceContains;
import static org.duckweedcoll.WebDriverAssistant.findElement;

public class Login_wd_ITCase extends WebDriverRoot {

    @Test
    public void test_pageWillAcceptUserNameAndPassword() {
        assertSourceContains(driver, "should have login message", "Log In Here");
        assertCssSelectorContains("should have correct title", driver, "h1", "Log In Here");

        findElement(driver, "username");
        findElement(driver, "password");
        findElement(driver, "submit");
    }

    @Test
    public void test_userNameAndPassword_willLogin() {
        findElement(driver, "username").sendKeys("andrew");
        findElement(driver, "password").sendKeys("password");
        findElement(driver, "submit").submit();
        assertSourceContains(driver, "should have login message", "Welcome, andrew");
    }


    @Before
    public void before() {
        driver.get("http://localhost:8080/login.groovy");
    }


}
