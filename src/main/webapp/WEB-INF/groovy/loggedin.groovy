import com.google.appengine.api.datastore.Query

log.info 'in loggedin.groovy'

request.getSession true

def id = user.getUserId()
def query = new Query('person')
query.addFilter("id", Query.FilterOperator.EQUAL, id)
def person = datastore.prepare(query).asSingleEntity();
if (person == null) {
    forward '/profile.groovy'
} else {
    session.setAttribute('person', person)
    forward '/'
}

        

