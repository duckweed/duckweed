import org.duckweedcoll.CircleHandler
import static org.junit.Assert.assertNotNull
import org.duckweedcoll.Circle

def circle = CircleHandler.makeCall(params, datastore, response) as Circle

circle.members = session.getAttribute('person').key

assertNotNull 'circle not found', circle
assertNotNull 'circle.name not found', circle.name
assertNotNull 'circle.desc not found', circle.description

include '/WEB-INF/includes/header.gtpl'

def header = 'Create A Circle'

html.html {
    h1 header
}
include '/WEB-INF/includes/person.gtpl'


html.html {
    form(method: 'post') {
        if (circle.id != null) {
            input(name: 'key', type: 'hidden', 'value': circle.id)
        }
        p('name')
        input(name: 'name', type: 'text', 'value': circle.name)
        p('description')
        textarea(name: 'description', rows: 5, cols: 50) {mkp.yield circle.description}
        input(name: 'submit', type: 'submit')
    }

    mkp.yield "secretary is: ${circle.secretary}"
}

include '/WEB-INF/includes/footer.gtpl'
