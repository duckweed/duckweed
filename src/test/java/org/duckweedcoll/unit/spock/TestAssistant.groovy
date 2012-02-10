package org.duckweedcoll.unit.spock

import com.google.appengine.api.datastore.Query
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: 11/02/12
 * Time: 6:52 AM
 * To change this template use File | Settings | File Templates.
 */
class TestAssistant {
    static List getAllCircles(def datastore) {
        def query = new Query("circle")
        def preparedQuery = datastore.prepare(query)
        List entities = preparedQuery.asList(withLimit(1))
        return entities
    }
}
