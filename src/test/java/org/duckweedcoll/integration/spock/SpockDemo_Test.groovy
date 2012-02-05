package org.duckweedcoll.integration.spock

import com.google.appengine.api.datastore.Query
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit
import org.duckweedcoll.spock.GaelykUnitSpec

class SpockDemo_Test extends GaelykUnitSpec {

    def setup() {
        groovlet 'bl/SpockDemo.groovy'
    }

    def "should add good user to ds"() {
        given:
        spockDemo.params.firstname = 'andrew'
        spockDemo.params.lastname = 'oxenburgh'
        spockDemo.get()

        when:
        List entities = getAllAndrews()

        then:
        !entities.isEmpty()
    }

    def "should have a first name: if not, don't add him to ds"() {
        given:
        spockDemo.params.lastname = 'oxenburgh'
        spockDemo.get()

        when:
        List entities = getAllAndrews()

        then:
        entities.isEmpty()
    }

    def "should have a last name: if not, don't add him to ds"() {
        given:
        spockDemo.params.firstname = 'andrew'
        spockDemo.get()

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