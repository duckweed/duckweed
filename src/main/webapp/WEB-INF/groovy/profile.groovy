import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Query

def query = new Query('user')
def id = user.getUserId()
query.addFilter("id", Query.FilterOperator.EQUAL, id)
def person = datastore.prepare(query).asSingleEntity();

if (person == null) {
    person = new Entity('user')
    person.setProperty 'id', id
    person.setProperty 'username', ''
    person.setProperty 'bio', ''
}

person << params

datastore.put person

session.setAttribute('person', person)

forward '/index.groovy'
