package org.duckweedcoll.demo
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
import com.google.appengine.api.datastore.Email
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Query
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import groovyx.gaelyk.GaelykBindings
import org.duckweedcoll.UserCircle
import org.junit.After
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertEquals

//http://code.google.com/appengine/docs/java/datastore/queries.html

//@Ignore
@GaelykBindings
class GaelykBindings_DemoTest {

    UserCircle userCircle = new UserCircle()


    @Test
    public void test_createAUser_retrieveIt() {
        def expectedEmail = new Email('andrew@andrew.com')
        def expectedAddress = '7 erehwon, nowhere'
        def expectedName = 'andrew'

        def entity = new Entity('user')
        entity.setProperty 'email', expectedEmail
        entity.setProperty 'address', expectedAddress
        entity.setProperty 'name', expectedName
        datastore.put entity

        Query query = new Query('user')
        Entity user = ((DatastoreService) datastore).prepare(query).asSingleEntity()

        assertEquals('should get correct email  ', expectedEmail, user.getProperty('email'))
        assertEquals('should get correct address', expectedAddress, user.getProperty('address'))
        assertEquals('should get correct name   ', expectedName, user.getProperty('name'))
    }


    LocalServiceTestHelper helper


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
