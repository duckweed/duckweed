package org.duckweedcoll

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.KeyFactory
import javax.servlet.http.HttpServletResponse
import static org.junit.Assert.assertNotNull

/**
 * Copyright AdScale GmbH, Germany, 2007
 */
class CreateCircle {

    static def makeCall(LinkedHashMap params, DatastoreService datastore, HttpServletResponse response) {
        assertNotNull params
        assertNotNull datastore
        assertNotNull response
        Entity circle = null
        if (params.isEmpty()) {
            circle =  createCircle(params, datastore)
        }
        else if (params['key'] != null) {
            circle = datastore.get(KeyFactory.createKey('circle', params['key'] as Integer))

            setProperty(params, circle, 'name')
            setProperty(params, circle, 'description')
            datastore.put circle
        }
        else {
            circle = createCircle(params, datastore)
        }
        assertNotNull 'circle is invalid here', circle
        assertNotNull 'circle is invalid here', circle.getProperty('name')
        assertNotNull 'circle is invalid here', circle.getProperty('description')

        circle
    }


    static private setProperty(LinkedHashMap params, Entity circle, String name) {
        if (params[name] != null) {
            circle.setProperty(name, params[name])
        }
        else if (circle.getProperty(name) == null) {
            circle.setProperty(name, '')
        }
    }


    static Entity createCircle(LinkedHashMap params, DatastoreService datastore) {
        Entity circle = new Entity('circle')
        setProperty(params, circle, 'name')
        setProperty(params, circle, 'description')
        datastore.put circle
        circle
    }

}
