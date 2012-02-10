package org.duckweedcoll.unit.spock

import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Key
import javax.servlet.http.HttpServletResponse
import org.duckweedcoll.util.spock.GaelykUnitSpec
import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertNotNull
import static org.duckweedcoll.unit.spock.TestAssistant.getAllCircles

// TODO:handle show circle - no parameters with a key - return filled circle

class EditCirclePage extends GaelykUnitSpec {
    private static final String NAME = 'name of the circle'
    private static final String DESC = 'description of the circle'

    def setup() {
        groovlet 'CircleHandler.groovy'
        Key key = createCircleKey()
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

    Key createCircleKey() {
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

class NewCirclePage extends GaelykUnitSpec {
    def setup() {
        groovlet 'CircleHandler.groovy'
        circleHandler.get()
    }

    def "redirect parameterless request to newcircle.groovy"() {
        given:
        def redirectedTo = ''
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

