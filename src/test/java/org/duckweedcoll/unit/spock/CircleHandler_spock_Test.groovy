package org.duckweedcoll.unit.spock

import com.google.appengine.api.datastore.Query
import javax.servlet.http.HttpServletResponse
import org.duckweedcoll.util.spock.GaelykUnitSpec
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit
import static junit.framework.Assert.assertEquals

import com.google.appengine.api.datastore.Entity

// TODO:handle show circle - no parameters with a key - return filled circle

class EditCirclePage extends GaelykUnitSpec {
    def setup() {
        groovlet 'bl/CircleHandler.groovy'
    }

    def "given key without other params, redirect to newcircle page"() {
        given:
        def circle = new Entity('circle')
        circle.name = 'name'
        circle.description = 'desc'
        circle.members = ''
        circle.secretary = 'secretary'
        datastore.put circle

        circleHandler.get()
        def redirectedTo
        circleHandler.response = ['sendRedirect': { redirectedTo = it }] as HttpServletResponse

        when:
        circleHandler.get()

        then:
        assertEquals '/newcircle.groovy', redirectedTo
    }
}

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




