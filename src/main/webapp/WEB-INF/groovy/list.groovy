import com.google.appengine.api.datastore.Entity

Entity entity = new Entity('person')

entity.firstname = 'andrew'
entity.lastname = 'oxenburgh'

entity.save()