import com.google.appengine.api.datastore.Entity


class List {
    Entity entity = new Entity('person')

    List() {
        entity.firstname = 'andrew'
        entity.lastname = 'oxenburgh'
        entity.save()
    }

    public static void main(String[] args) {
    }

    public void hello(){
        print 'hello, world'
    }
}