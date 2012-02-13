package org.duckweedcoll;


import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Key
import static org.junit.Assert.assertNotNull
import com.google.appengine.api.datastore.Query
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit

circle = new Entity('circle')

if (showNewCirclePage()) {
    log.info 'showing new circle'
    request.setAttribute('circle', new Circle())
    response.sendRedirect('/newcircle.groovy')
    return
}

if (params.size() == 1 && params.containsKey('key')) {
    log.info 'getting existing circle'
    Key key = params.get('key') as Key
    circle = datastore.get key
    assertNotNull circle
    assertNotNull circle.name
    request.setAttribute('circle', circle)
    response.sendRedirect('/newcircle.groovy')
    return
}

log.info 'preparing for new circle'

circle.name = params.name
circle.description = params.description
circle.members = ''
circle.secretary = 'something'

String kind = "circle"
String property = "name"
String value = params.name
def query = new Query(kind)
query.addFilter(property, Query.FilterOperator.EQUAL, value)
def preparedQuery = datastore.prepare(query)
List entities = preparedQuery.asList(withLimit(1))
boolean duplicateName = !entities.isEmpty()

if(duplicateName){
    request.setAttribute('errors', ['name duplicated'])
    response.sendRedirect '/newcircle.groovy'
    request.setAttribute('circle', circle)
    return
}

circle.save()

request.setAttribute('circle', circle)

response.sendRedirect '/'

//**********************************************************************
private showNewCirclePage() {
    params.isEmpty()
}
