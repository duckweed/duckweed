package org.duckweedcoll.unit

import com.google.appengine.api.datastore.Entity
import groovyx.gaelyk.GaelykBindings

import org.junit.Test
import static org.junit.Assert.assertEquals
import org.duckweedcoll.CircleHandler

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

@GaelykBindings
class Circle_rules_Test extends GaelykTestBase {

    @Test
    public void mandatoryFieldsShouldBeAdded() throws Exception {
        assertMandatoryFieldAdded('name')
        assertMandatoryFieldAdded('description')
    }

    @Test
    public void unknownFieldsShouldNotBeAdded() throws Exception {
        def circle = new Entity('circle')
        assertEquals 'precond:null beforehand', null, circle.getProperty('description')
        CircleHandler.fillCircle(['unknown_field': 'lkjsdlfkj'], circle)
        assertEquals 'still null', null, circle.getProperty('unknown_field')
    }

    @Test
    public void mixtureOfMandatoryAndUnknownFields() throws Exception {
        def circle = new Entity('circle')
        CircleHandler.fillCircle(['name': 'name field', 'unknown_field': 'unknown field'], circle)
        assertEquals 'should find new name', 'name field', circle.getProperty('name')
        assertEquals 'should be empty', '', circle.getProperty('description')
        assertEquals 'should be null', null, circle.getProperty('unknown_field')
    }

    private assertMandatoryFieldAdded(String property) {
        def circle = new Entity('circle')
        assertEquals null, circle.getProperty(property)
        CircleHandler.fillCircle([:], circle)
        assertEquals '', circle.getProperty(property)
    }

}
