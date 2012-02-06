package org.duckweedcoll;

import com.google.appengine.api.datastore.Entity
import org.duckweedcoll.Circle
import static org.junit.Assert.fail

circle = new Entity('circle')

if (showNewCirclePage()) {
    request.setAttribute('circle', new Circle())
    response.sendRedirect('/newcircle.groovy')
    return
}

circle.name = params.name
circle.description = params.description
circle.members = ''
circle.secretary = 'something'
circle.save()

request.setAttribute('circle', circle)

response.sendRedirect '/'

//**********************************************************************
private showNewCirclePage() {
    params.isEmpty()
}
