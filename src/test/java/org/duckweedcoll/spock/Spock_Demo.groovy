package org.duckweedcoll.spock

import com.google.appengine.api.datastore.Query
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit

class Spock_Demo extends GaelykUnitSpec {

    def setup() {
        groovlet 'list.groovy', 'src/main/webapp/WEB-INF/groovy'
    }

    def "the datastore is used from within the groovlet"() {
        given: "the initialised groovlet is invoked and data is persisted"
        list.get()

        when: "the datastore is queried for data"
        def query = new Query("person")
        query.addFilter("firstname", Query.FilterOperator.EQUAL, "andrew")
        def preparedQuery = datastore.prepare(query)
        def entities = preparedQuery.asList(withLimit(1))

        then: "the persisted data is found in the datastore"
        def person = entities[0]
        person.firstname == 'andrew'
        person.lastname == 'oxenburgh'
    }
}