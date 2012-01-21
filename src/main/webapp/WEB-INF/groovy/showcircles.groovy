import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.FetchOptions
import com.google.appengine.api.datastore.Query

def query = new Query('circle')
def circles = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100))

include '/WEB-INF/includes/header.gtpl'

html.html {
    h1 'Show Circles'
    table {
        p("found ${circles.size()} circles")
        circles.each {
            Entity circle ->
            tr {
                td {
                    p(circle.name)
                }
            }
        }
    }
}

include '/WEB-INF/includes/footer.gtpl'
