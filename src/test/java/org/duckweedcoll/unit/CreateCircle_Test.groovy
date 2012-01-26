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

/**
 * Copyright AdScale GmbH, Germany, 2007
 */

@GaelykBindings
class CreateCircle_Test {

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
    public void demoAllTypesOfCalls() throws Exception {
        def circle = createCircle(['name': 'name', 'description': 'description'], datastore)

        assertCallReturnsGoodCircle(['key': circle.key.id])
        assertCallReturnsGoodCircle(['name': 'name here'])
        assertCallReturnsGoodCircle([:])
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
