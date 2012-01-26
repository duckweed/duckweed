import org.duckweedcoll.CreateCircle
import static org.junit.Assert.assertNotNull

log.info 'entering newcircle - params to follow:'

params.each {
    String key, String value ->
    log.info "$key, $value"
}


def circle = CreateCircle.makeCall(params, datastore, response)

assertNotNull 'circle not found', circle
assertNotNull 'circle.name not found', circle.name
assertNotNull 'circle.desc not found', circle.description

include '/WEB-INF/includes/header.gtpl'

html.html {
    h1 'Create A Circle'
    form(method: 'post') {
        p('name')
        input(name: 'name', type: 'text',)
        p('description')
        textarea(name: 'description', rows: 5, cols: 50) {
            p(circle.description)
        }
        input(name: 'submit', type: 'submit')
    }
}

include '/WEB-INF/includes/footer.gtpl'
