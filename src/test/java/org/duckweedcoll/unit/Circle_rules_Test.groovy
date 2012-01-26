package org.duckweedcoll.unit

import com.google.appengine.api.datastore.Entity
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import groovyx.gaelyk.GaelykBindings
import org.duckweedcoll.Circle
import org.junit.After
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertEquals

/**
 * Copyright AdScale GmbH, Germany, 2007
 */

@GaelykBindings
class Circle_rules_Test {

    @Test
    public void mandatoryFieldsShouldBeAdded() throws Exception {
        assertMandatoryFieldAdded('name')
        assertMandatoryFieldAdded('description')
    }

    @Test
    public void unknownFieldsShouldNotBeAdded() throws Exception{
        def circle = new Entity('circle')
        assertEquals 'precond:null beforehand', null, circle.getProperty('description')
        Circle.fillCircle(['unknown_field':'lkjsdlfkj'], circle)
        assertEquals 'still null', null, circle.getProperty('unknown_field')
    }

    @Test
    public void mixtureOfMandatoryAndUnknownFields() throws Exception{
        def circle = new Entity('circle')
        Circle.fillCircle(['name':'name field', 'unknown_field':'unknown field'], circle)
        assertEquals 'should find new name', 'name field', circle.getProperty('name')
        assertEquals 'should be empty', '', circle.getProperty('description')
        assertEquals 'should be null', null, circle.getProperty('unknown_field')
    }



    private assertMandatoryFieldAdded(String property) {
        def circle = new Entity('circle')
        assertEquals null, circle.getProperty(property)
        Circle.fillCircle([:], circle)
        assertEquals '', circle.getProperty(property)
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
