package org.duckweedcoll.unit.spock

import com.google.appengine.api.datastore.Query
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit
import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.Entity

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: 11/02/12
 * Time: 6:52 AM
 * To change this template use File | Settings | File Templates.
 */
class TestAssistant {

    static final String NAME = 'name of the circle'
    static final String DESC = 'description of the circle'

    static List getAllCircles(def datastore) {
        def query = new Query("circle")
        def preparedQuery = datastore.prepare(query)
        List entities = preparedQuery.asList(withLimit(100))
        return entities
    }

    static Key createCircleKey(def datastore) {
        def circle = new Entity('circle')
        circle.name = NAME
        circle.description = DESC
        circle.members = ''
        circle.secretary = 'secretary'
        datastore.put circle

        def key = circle.key
        return key
    }

}
