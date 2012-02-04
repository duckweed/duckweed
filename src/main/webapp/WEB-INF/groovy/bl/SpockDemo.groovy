import com.google.appengine.api.datastore.Entity

def firstname = params.firstname
def lastname = params.lastname

if (firstname == null) {
    return
}
if (lastname == null) {
    return
}

Entity entity = new Entity('person')

entity.firstname = firstname
entity.lastname = lastname
entity.save()

