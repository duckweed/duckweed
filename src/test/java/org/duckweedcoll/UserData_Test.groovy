package org.duckweedcoll

import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.FetchOptions
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Query
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import groovyx.gaelyk.GaelykBindings
import org.junit.After
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertEquals
import com.google.appengine.api.datastore.Key

@GaelykBindings
class UserData_Test {

    LocalServiceTestHelper helper

    @Test
    public void test_createAUser_andRetrieveIt() {
        def expectedEmail = 'andrew@andrew.com'
        def expectedAddress = '7 erehwon, nowhere'
        def expectedName = 'andrew'

        def user = KeyFactory.createKey 'users', 'users'

        createAndSaveUser(user, expectedEmail, expectedAddress, expectedName)
        List<Entity> greetings = getUser(user);

        assertEquals('should get correct email', expectedEmail, greetings[0].getProperty('email'))
        assertEquals('should get correct address', expectedAddress, greetings[0].getProperty('address'))
        assertEquals('should get correct name', expectedName, greetings[0].getProperty('name'))
    }

    private List<Entity> getUser(Key user) {
        Query query = new Query('user', user)
        List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5))
        return greetings
    }

    private createAndSaveUser(Key user, String expectedEmail, String expectedAddress, String expectedName) {
        def entity = new Entity('user', user)
        entity.setProperty 'email', expectedEmail
        entity.setProperty 'address', expectedAddress
        entity.setProperty 'name', expectedName

        datastore.put entity
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