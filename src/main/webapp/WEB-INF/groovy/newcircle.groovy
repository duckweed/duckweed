import com.google.appengine.api.datastore.Entity

if (params.submit == 'Submit') {
    if (params.name == '') {
        // error
    } else {
        def circle = new Entity('circle')
        circle << params
        datastore.put circle
        redirect '/'
    }
}


include '/WEB-INF/includes/header.gtpl'

html.html {
    h1 'Create A Circle'
    form() {
        p('name')
        input(name: 'name', type: 'text')
        p('description')
        textarea(name: 'description', rows: 5, cols: 50)
        input(name: 'submit', type: 'submit')
    }
}

include '/WEB-INF/includes/footer.gtpl'
