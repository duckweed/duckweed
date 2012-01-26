package org.duckweedcoll.unit

import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Query
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import groovyx.gaelyk.GaelykBindings
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import org.junit.After
import org.junit.Before
import org.junit.Test
import static org.duckweedcoll.CreateCircle.createCircle
import static org.duckweedcoll.CreateCircle.makeCall
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.fail

/**
 * Copyright AdScale GmbH, Germany, 2007
 */

@GaelykBindings
class Circle_general_Test {


    @Test
    public void testInitial() throws Exception{
//        fail('aaaaaaaaaaaaaaaaahhhhhhhhhhhhhhhhh!!!!!!!!!!!!!!!!!')
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
