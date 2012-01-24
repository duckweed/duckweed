import com.google.appengine.api.datastore.Entity

def circle = new Entity('circle')

if (params.submit != null) {
    if (params.name == '') {
        // error
    } else {
        circle = new Entity('circle')
        circle << params
        datastore.put circle
        redirect '/'
    }
} else {
    if (params.key != null) {
//        KeyFactory.createKey('circle')
        circle = datastore.get('circle', params.key as Integer)
    }
}

include '/WEB-INF/includes/header.gtpl'

html.html {
    h1 'Create A Circle'
    form(method: 'post') {
        p('name')
        input(name: 'name', type: 'text', )
        p('description')
        textarea(name: 'description', rows: 5, cols: 50) {
            p(circle.description)
        }
        input(name: 'submit', type: 'submit')
    }
}

include '/WEB-INF/includes/footer.gtpl'
