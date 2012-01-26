import com.google.appengine.api.datastore.Query
import com.google.appengine.api.datastore.FetchOptions
import com.google.appengine.api.datastore.Entity

include '/WEB-INF/includes/header.gtpl'

html.html{
    h1 'show everything'
    body{
        def query = new Query('user')
        def users = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1000))
        h2 "users (${users.size()})"

        users.each{
            Entity user ->
            p(user)
        }

        query = new Query('circle')
        def circles = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1000))

        h2 "circles (${circles.size()})"
        circles.each{
            Entity circle ->
            p(circle)
        }
    }
}

include '/WEB-INF/includes/footer.gtpl'
