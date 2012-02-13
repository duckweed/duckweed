package org.duckweedcoll.unit.spock

import com.google.appengine.api.datastore.Key
import javax.servlet.http.HttpServletResponse
import org.duckweedcoll.util.spock.GaelykUnitSpec
import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertNotNull
import static org.duckweedcoll.unit.spock.TestAssistant.*

// TODO:handle show circle - no parameters with a key - return filled circle

class ShowEditCirclePage extends GaelykUnitSpec {

    def setup() {
        groovlet 'CircleHandler.groovy'
        Key key = createCircleKey(datastore)
        circleHandler.params.put('key', key as String)
    }

    def "given key without other params, newcircle page should have circle attributes"() {
        when:
        circleHandler.get()

        then:
        assertNotNull 'should find a circle', circleHandler.circle
        assertNotNull 'should find a name', circleHandler.circle.name
        assertNotNull 'should find a name', circleHandler.circle.description
        assertEquals 'wrong name', circleHandler.circle.name, NAME
        assertEquals 'wrong desc', circleHandler.circle.description, DESC

    }

    def "given key without other params, redirect to newcircle page"() {
        def redirectedTo = ''

        given:
        circleHandler.response = ['sendRedirect': { redirectedTo = it }] as HttpServletResponse

        when:
        circleHandler.get()

        then:
        assertEquals '/newcircle.groovy', redirectedTo
    }

}

class NewCirclePage extends GaelykUnitSpec {
    def redirectedTo = ''

    def setup() {
        groovlet 'CircleHandler.groovy'
        circleHandler.response = ['sendRedirect': { redirectedTo = it }] as HttpServletResponse
    }

    def "redirect parameterless request to newcircle.groovy"() {
        given:

        when:
        circleHandler.get()

        then:
        assertEquals '/newcircle.groovy', redirectedTo
    }

    def "return empty circle into request attributes"() {
        when:
        circleHandler.get()
        def returnedCircle = circleHandler.request.getAttribute('circle')

        then:
        assertEquals 'should have empty name', "".toString(), returnedCircle.name.toString()
        assertEquals 'should have empty description', "".toString(), returnedCircle.description.toString()
    }

    def "cannot create circle with name of already existing one"() {
        when:
        assertEquals 0, getAllCircles(datastore).size()
        createCircleKey(datastore)
        assertEquals 'precond: there should be one circle', 1, getAllCircles(datastore).size()
        circleHandler.params.name = NAME
        circleHandler.get()

        def errors = circleHandler.request.getAttribute('errors')
        then:
        assertNotNull 'should find a duplicate name error', errors
        assertEquals 'should be one error', 1, errors.size()
        assertEquals 'error should be correct wording', 'name duplicated', errors[0]
        assertEquals 'the new circle should not have been saved', 1, getAllCircles(datastore).size()
        assertEquals 'duplicate error shoud direct back to new circle page', '/newcircle.groovy', redirectedTo
    }
}

class CreateNewCircle extends GaelykUnitSpec {
    def setup() {
        groovlet 'CircleHandler.groovy'
        circleHandler.params.name = ''
    }

    def "redirect home"() {
        given:
        def redirectedTo = ''
        circleHandler.response = ['sendRedirect': { redirectedTo = it }] as HttpServletResponse

        when:
        circleHandler.get()

        then:
        assertEquals '/', redirectedTo
    }

    def "create new circle in ds."() {
        given:
        circleHandler.get()

        when:
        List entities = getAllCircles(datastore)

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
        List entities = getAllCircles(datastore)

        then:
        assertEquals 'should have created a circle with the right name', expectedName, entities[0].name
        assertEquals 'should have created a circle with the right desc', expectedDesc, entities[0].description
        entities[0].members != null
    }
}

