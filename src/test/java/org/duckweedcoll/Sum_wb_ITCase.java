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
import org.openqa.selenium.By;

import static org.duckweedcoll.WebDriverAssistant.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Sum_wb_ITCase extends WebDriverRoot {

    @Test
    public void test_whenDigitsAreEntered_thenSummedCorrectly() {
        checkSumming("simple sum", driver, "1", "2", "3");
        checkSumming("same numbers", driver, "2", "2", "4");
        checkSumming("pos and neg", driver, "-2", "2", "0");
        checkSumming("neg and pos", driver, "2", "-2", "0");
        checkSumming("unneeded prefix zeros", driver, "02", "02", "4");
        checkSumming("double digits", driver, "17", "19", "36");
        checkSumming("preceding space in 'A'", driver, " 17", "19", "36");
        checkSumming("preceding space in 'B'", driver, "17", " 19", "36");
        checkSumming("preceding space in both", driver, " 17", " 19", "36");
    }

    @Test
    public void test_whenNonNumericIsEntered_thenThrowException() {
        driver.findElement(By.name("a")).sendKeys("non numeric");
        driver.findElement(By.name("Send")).submit();

        assertSourceContains(driver, "should throw a numeric exception", "NumberFormatException");
    }

    @Test
    public void test_structure() {
        assertEquals("Sum Me", getCssSelectorText(driver, "h1"));
    }


    @Before
    public void before() {
        driver.get("http://localhost:8080/sum.groovy");
    }
}
