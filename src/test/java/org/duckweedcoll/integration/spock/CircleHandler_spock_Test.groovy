package org.duckweedcoll.integration.spock

import com.google.appengine.api.datastore.Query
import javax.servlet.http.HttpServletResponse
import org.duckweedcoll.spock.GaelykUnitSpec
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit
import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertNotNull

// TODO:handle edit circle - all parameters with a key - redirect to homepage - edit circle in ds
// TODO:handle show circle - no parameters with a key - return filled circle

class NewCirclePage extends GaelykUnitSpec {
    def setup() {
        groovlet 'bl/CircleHandler.groovy'
        circleHandler.get()
    }

    def "redirect parameterless request to newcircle.groovy"() {
        given:
        def redirectedTo
        circleHandler.response = ['sendRedirect': { redirectedTo = it }] as HttpServletResponse

        when:
        circleHandler.get()

        then:
        assertEquals '/newcircle.groovy', redirectedTo
    }

    def "return empty circle into request attributes"() {
        when:
        def returnedCircle = circleHandler.request.getAttribute('circle')

        then:
        assertEquals 'should have empty name', "".toString(), returnedCircle.name.toString()
        assertEquals 'should have empty description', "".toString(), returnedCircle.description.toString()

    }
}

class CreateNewCircle extends GaelykUnitSpec {
    def setup() {
        groovlet 'bl/CircleHandler.groovy'
        circleHandler.params.name = ''
    }

    def "redirect home"() {
        given:
        def redirectedTo
        circleHandler.response = ['sendRedirect': { redirectedTo = it }] as HttpServletResponse

        when:
        circleHandler.get()
        List entities = getAllCircles()

        then:
        assertEquals '/', redirectedTo
    }

    def "create new circle in ds."() {
        given:
        circleHandler.get()

        when:
        List entities = getAllCircles()

        then:
        assertEquals 'should have created a circle', 1, entities.size()
    }

    def "circle in ds should have description passed in param list"() {
        final expectedName = 'some name here'
        final expectedDesc = 'some desc here'

        given:
        circleHandler.params.description = expectedDesc
        circleHandler.params.name = expectedName
        circleHandler.get()

        when:
        List entities = getAllCircles()

        then:
        assertEquals 'should have created a circle with the right name', expectedName, entities[0].name
        assertEquals 'should have created a circle with the right desc', expectedDesc, entities[0].description
        entities[0].members != null
    }

    private List getAllCircles() {
        def query = new Query("circle")
        def preparedQuery = datastore.prepare(query)
        List entities = preparedQuery.asList(withLimit(1))
        return entities
    }
}




