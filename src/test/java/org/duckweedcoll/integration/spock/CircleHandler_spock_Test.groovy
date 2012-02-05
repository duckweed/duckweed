package org.duckweedcoll.integration.spock

class CircleHandler_spock_Test extends GaelykUnitSpec {

    def setup() {
        groovlet 'bl/CircleHandler.groovy'
    }

    def "wish to save a circle. redirect home"() {
        given:
        def redirectedTo
        circleHandler.response = ['sendRedirect': { redirectedTo = it }] as HttpServletResponse

        when:
        circleHandler.get()
        def query = new Query("circle")
        def preparedQuery = datastore.prepare(query)
        List entities = preparedQuery.asList(withLimit(1))

        then:
        assertEquals '\\', redirectedTo
    }

    def "wish to save a circle. create new circle in ds."() {
        given:
        circleHandler.get()

        when:
        List entities = getAllCircles()

        then:
        assertEquals 'should have created a circle', 1, entities.size()
    }

    def "wish to save a circle. circle in ds should have name passed in param list"() {
        final expectedName = 'some name here'
        given:
        circleHandler.params.name = expectedName
        circleHandler.get()

        when:
        List entities = getAllCircles()

        then:
        assertEquals 'should have created a circle with the right name', expectedName, entities[0].name
    }

    def "wish to save a circle. circle in ds should have description passed in param list"() {
        final expectedDesc = 'some name here'
        given:
        circleHandler.params.description = expectedDesc
        circleHandler.get()

        when:
        List entities = getAllCircles()

        then:
        assertEquals 'should have created a circle with the right name', expectedDesc, entities[0].description
    }

    private List getAllCircles() {
        def query = new Query("circle")
        def preparedQuery = datastore.prepare(query)
        List entities = preparedQuery.asList(withLimit(1))
        return entities
    }

    // TODO:handle new circle - no parameters are set at all - return blank circle
    // TODO:handle create circle - all parameters, but no key - redirect to home page - create new circle in ds
    // TODO:handle edit circle - all parameters with a key - redirect to homepage - edit circle in ds
    // TODO:handle show circle - no parameters with a key - return filled circle

}

import com.google.appengine.api.datastore.Query
import javax.servlet.http.HttpServletResponse
import org.duckweedcoll.spock.GaelykUnitSpec
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit
import static junit.framework.Assert.assertEquals

