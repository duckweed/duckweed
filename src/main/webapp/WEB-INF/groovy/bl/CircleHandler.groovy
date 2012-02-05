import com.google.appengine.api.datastore.Entity
import org.duckweedcoll.Circle

final circle = new Entity('circle')

if(params.isEmpty()){
    
    request.setAttribute('circle', new Circle())
    
    response.sendRedirect('/newcircle.groovy')
    return
}

circle.name = params.name
circle.description = params.description

circle.save()

response.sendRedirect '/'
