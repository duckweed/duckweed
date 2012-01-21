import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Query
import static org.junit.Assert.assertNotNull

if (user != null && request.session.getAttribute('person') == null) {
    Entity person = request.session.getAttribute('person')
    if (person == null) {
        def id = user.getUserId()
        def query = new Query('user')
        query.addFilter("id", Query.FilterOperator.EQUAL, id)
        person = datastore.prepare(query).asSingleEntity();
        if(person == null){
            person = new Entity('user')
            person.setProperty 'id', id
            person.setProperty 'username', ''
            person.setProperty 'bio', ''
            datastore.put person
        }
        request.session.person = person
        assertNotNull "no person in db", person
    }
    assertNotNull "no person", request.session.person
}

forward '/index.gtpl'
