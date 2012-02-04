package org.duckweedcoll.integration

import com.google.appengine.api.datastore.Query
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit
import org.duckweedcoll.spock.GaelykUnitSpec

class SpockDemo_Test extends GaelykUnitSpec {

    def setup() {
        groovlet 'bl/SpockDemo.groovy'
    }

    def "should add good user to ds"() {
        given:
        SpockDemo.params.firstname = 'andrew'
        SpockDemo.params.lastname = 'oxenburgh'
        SpockDemo.get()

        when:
        List entities = getAllAndrews()

        then:
        !entities.isEmpty()
    }

    def "should have a first name: if not, don't add him to ds"() {
        given:
        SpockDemo.params.lastname = 'oxenburgh'
        SpockDemo.get()

        when:
        List entities = getAllAndrews()

        then:
        entities.isEmpty()
    }

    def "should have a last name: if not, don't add him to ds"() {
        given:
        SpockDemo.params.firstname = 'andrew'
        SpockDemo.get()

        when:
        List entities = getAllAndrews()

        then:
        entities.isEmpty()
    }

    def getAllAndrews() {
        def query = new Query("person")
        def preparedQuery = datastore.prepare(query)
        List entities = preparedQuery.asList(withLimit(1))
        return entities
    }
}