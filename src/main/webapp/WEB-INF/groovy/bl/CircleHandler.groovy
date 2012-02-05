import com.google.appengine.api.datastore.Entity

final circle = new Entity('circle')

circle.name = params.name
circle.description = params.description

circle.save()

response.sendRedirect '\\'
