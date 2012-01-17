package org.duckweedcoll
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

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.FetchOptions
import com.google.appengine.api.datastore.Query
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import groovyx.gaelyk.GaelykBindings
import org.junit.After
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertEquals

//http://code.google.com/appengine/docs/java/datastore/queries.html

@GaelykBindings
class UserCircle_Test {

    LocalServiceTestHelper helper
    UserCircle userCircle = new UserCircle()


    @Test
    public void test_createAUser_retrieveIt() {
        def expectedEmail = 'andrew@andrew.com'
        def expectedAddress = '7 erehwon, nowhere'
        def expectedName = 'andrew'

        datastore.put userCircle.createUser(expectedName, expectedAddress, expectedEmail)

        Entity user = findUniqueUser();

        assertEquals('should get correct email  ', expectedEmail, user.getProperty('email'))
        assertEquals('should get correct address', expectedAddress, user.getProperty('address'))
        assertEquals('should get correct name   ', expectedName, user.getProperty('name'))
    }


    @Test
    public void test_createTwoUsers_findTwoUsers() throws Exception {
        create2users()
        List<Entity> results = datastore.prepare(new Query('user')).asList(FetchOptions.Builder.withLimit(5))
        assertEquals("there should be only two", 2, results.size())
    }


    @Test
    public void test_createSeveralUsers_filterForOne() throws Exception {
        create2users()

        def query = new Query('user')
        query.addFilter("name", Query.FilterOperator.EQUAL, "steven")
        def results = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5))

        assertEquals("there should be only one", 1, results.size())
        assertEquals('steven', results[0].getProperty('name'))
        assertEquals('perth', results[0].getProperty('address'))
        assertEquals('steven@perth.com.au', results[0].getProperty('email'))
    }

    @Test
    public void test_createCircle_oneOwner() throws Exception {
        def user = userCircle.createUser('andrew', '', '')
        datastore.put user

        Entity circle = userCircle.createCircle('general circle', user)

        assertEquals 'general circle', circle.getProperty('name')
        def owner = datastore.get circle.getProperty('owner')
        assertEquals(owner, user)
    }

    @Test
    public void test_aUserMayHaveTwoCircles() throws Exception {
        Entity user1
        Entity user2
        (user1, user2) = create2users()

        userCircle.createCircle('c1', user1)
        def circle2 = userCircle.createCircle('c2', user2)

        userCircle.addCircleToUser(user1, circle2)

        def user1circles = user1.getProperty 'circles'

        assertEquals 2, user1circles.size()
    }


    @Test
    public void test_aCircleMayHaveTwoOwners() throws Exception {
        Entity user1
        Entity user2
        (user1, user2) = create2users()

        userCircle.createCircle('c1', user1)
        def circle2 = userCircle.createCircle('c2', user2)

        userCircle.addOwnerToCircle(circle2, user2)

        def circle2owners = circle2.getProperty 'owner'

        assertEquals 2, circle2owners.size()
    }

    @Test
    public void test_aUserMayHaveThreeCircles() throws Exception {

        Entity user1
        Entity user2

        (user1, user2) = create2users()

        userCircle.createCircle('c1', user1)
        def circle2 = userCircle.createCircle('c2', user2)
        def circle3 = userCircle.createCircle('c3', user2)

        userCircle.addCircleToUser(user1, circle2)
        userCircle.addCircleToUser(user1, circle3)

        def user1circles = user1.getProperty 'circles'
        assertEquals 3, user1circles.size()
    }

    private create2users() {
        def user1 = userCircle.createUser('steven', 'perth', 'steven@perth.com.au')
        def user2 = userCircle.createUser('philip', 'hilaries', 'philip@hillaries.com.au')
        datastore.put user1
        datastore.put user2

        return [user1, user2]
    }


    private Entity findUniqueUser() {
        Query query = new Query('user')
        List<Entity> greetings = ((DatastoreService) datastore).prepare(query).asList(FetchOptions.Builder.withLimit(5))
        return greetings[0]
    }

    @Before
    void before() {
        helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
        helper.setUp()
    }


    @After
    void after() {
        helper.tearDown()
    }
}
