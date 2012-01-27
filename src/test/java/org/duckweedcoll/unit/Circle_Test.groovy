package org.duckweedcoll.unit

import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Query
import groovyx.gaelyk.GaelykBindings
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import org.junit.Test
import static org.duckweedcoll.Circle.createCircle
import static org.duckweedcoll.Circle.makeCall
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNull
import org.junit.Ignore

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
class Circle_Test extends GaelykTestBase {

    Map params = [:]

    def redirect = 'unredirected'

    HttpSession session = [:] as HttpSession
    HttpServletRequest request = [:] as HttpServletRequest

    HttpServletResponse response = ['sendRedirect': {redirect = it}] as HttpServletResponse


    @Test
    public void ifNewCall_createCircle() throws Exception {
        Entity circle = makeCall([:], datastore, response)
        assertNotNull(circle)
        assertEquals '', circle.getProperty('name')
        assertNotNull '', circle.getProperty('description')
    }


    @Test
    public void ifNoKey_putCircleInDatastore() throws Exception {
        params = ['name': 'some name']
        makeCall(params, datastore, response)
        Entity circle = getSingleCircle()

        assertNotNull 'expecting a circle', circle
        assertEquals 'some name', circle.getProperty('name')
    }


    @Test
    public void ifKeyFound_getCircleFromDatastore() throws Exception {
        Entity circle = createCircle(['name': 'some name'], datastore)
        makeCall(['key': circle.key.id], datastore, response)
        def newCircle = getSingleCircle()
        assertNotNull newCircle
        assertEquals 'some name', newCircle.getProperty('name')
    }


    @Test
    public void ifKeyFound_resetDataOnDatastore() throws Exception {
        Entity circle = createCircle(['name': 'some name'], datastore)
        makeCall(['key': circle.key.id, 'name': 'new name'], datastore, response)
        def newCircle = getSingleCircle()
        assertNotNull newCircle
        assertEquals 'new name', newCircle.getProperty('name')
    }


    @Test
    public void ifSubmitted_shouldRedirectToHome() throws Exception {
        makeCall(['submit': 'someVal', 'name': 'new name'], datastore, response)
        assertEquals('should have redirected', '/', redirect)
        def circle = getSingleCircle()
        assertNotNull circle
        assertNotNull circle.key
        assertNotNull circle.getProperty('name')
        assertNotNull circle.getProperty('description')
    }


    @Test
    public void ifSubmittedWithKey_updateThatKey() throws Exception {
        def iniCircle = createCircle(['name': 'name', 'description': 'description'], datastore)

        makeCall(['submit': 'someVal', 'name': 'new name', 'description': 'new description', 'key': iniCircle.key.id], datastore, response)
        assertEquals('should have redirected', '/', redirect)
        def circle = getSingleCircle()
        assertNotNull 'should find one circle', circle
        assertEquals 'keys should be equal', iniCircle.key.id, circle.key.id
        assertEquals 'name should be updated', 'new name', circle.getProperty('name')
        assertEquals 'description should be updated', 'new description', circle.getProperty('description')
    }


    @Test
    public void ifNoKeyFound_NoCircleAdded() throws Exception {
        makeCall([:], datastore, response)
        assertNull 'should not add a circle to datastore', getSingleCircle()
    }


    @Test
    public void demoAllTypesOfCalls() throws Exception {
        def circle = createCircle(['name': 'name', 'description': 'description'], datastore)

        assertCallReturnsGoodCircle(['key': circle.key.id])
        assertCallReturnsGoodCircle(['name': 'name here'])
        assertCallReturnsGoodCircle([:])
    }


    @Test
    @Ignore('trying out adding a member to a circle')
    public void newCircleShouldHaveAMember() throws Exception {
        def person1 = new Entity('person')
        def person2 = new Entity('person')
        def circle = new Entity('circle')
        circle.setProperty('members', person1.key)

        datastore.put person1
        datastore.put person2
        datastore.put circle
    }

    private assertCallReturnsGoodCircle(LinkedHashMap prm) {
        def returnedEntity = makeCall(prm, datastore, response)
        assertNotNull returnedEntity
        assertNotNull returnedEntity.getProperty('name')
        assertNotNull returnedEntity.getProperty('description')
    }


    private Entity getSingleCircle() {
        Query q = new Query('circle')
        Entity circle = datastore.prepare(q).asSingleEntity()
        return circle
    }
}
