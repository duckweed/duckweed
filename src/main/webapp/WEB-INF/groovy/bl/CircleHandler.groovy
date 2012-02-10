package org.duckweedcoll;

import com.google.appengine.api.datastore.Entity
import org.duckweedcoll.Circle
import static org.junit.Assert.fail
import com.google.appengine.api.datastore.Key
import static org.junit.Assert.assertNotNull

circle = new Entity('circle')

if (showNewCirclePage()) {
    log.info 'showing new circle'
    request.setAttribute('circle', new Circle())
    response.sendRedirect('/newcircle.groovy')
    return
}

if(params.size()==1 && params.containsKey('key')){
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
circle.save()

request.setAttribute('circle', circle)

response.sendRedirect '/'

//**********************************************************************
private showNewCirclePage() {
    params.isEmpty()
}
