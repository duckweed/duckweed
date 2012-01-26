import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.FetchOptions
import com.google.appengine.api.datastore.Query

def query = new Query('circle')
def circles = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1000))

include '/WEB-INF/includes/header.gtpl'

html.html { h1 'Show Circles' }
include '/WEB-INF/includes/person.gtpl'


html.html {
    table {
        h2 "found ${circles.size()} circles"
        circles.each {
            Entity circle ->
            tr {
                td('name': 'circle-name') {
                    a(href: "newcircle.groovy?key=$circle.key.id") {
                        p(circle.name)
                    }
                }
                td('name': 'circle-description') { p(circle.description) }
            }
        }
    }
}

include '/WEB-INF/includes/footer.gtpl'
