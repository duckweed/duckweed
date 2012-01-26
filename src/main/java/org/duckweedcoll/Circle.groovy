package org.duckweedcoll

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.KeyFactory
import javax.servlet.http.HttpServletResponse
import static org.junit.Assert.assertNotNull

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

class Circle {

    static List MANDATORY_FIELDS = ['name', 'description']


    static def makeCall(LinkedHashMap params, DatastoreService datastore, HttpServletResponse response) {
        assertNotNull params
        assertNotNull datastore
        assertNotNull response
        Entity circle = null
        if (params.isEmpty()) {
            circle = new Entity('circle')
            fillCircle(params, circle)
            circle
        }
        else if (params['key'] != null) {
            circle = datastore.get(KeyFactory.createKey('circle', params['key'] as Integer))
            fillAndSaveCircle(datastore, params, circle)
        }
        else {
            circle = createCircle(params, datastore)
        }

        circle
    }


    static fillAndSaveCircle(DatastoreService datastore, LinkedHashMap params, Entity circle) {
        fillCircle(params, circle)
        datastore.put circle
    }


    static fillCircle(LinkedHashMap params, Entity circle) {
        MANDATORY_FIELDS.each {
            setProperty(params, circle, it)
        }
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
