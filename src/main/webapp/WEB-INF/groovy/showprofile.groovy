include '/WEB-INF/includes/header.gtpl'


html.html { h1 'New User Details' }
include '/WEB-INF/includes/person.gtpl'

html.html {
    form(action: 'profile.groovy') {
        p('Nick Name')
        input(type: 'text', name: 'username', value:request.session.person.username)
        p('Bio')
        textarea(name: 'bio', rows: 10, cols: 100) { mkp.yield request.session.person.bio }
        input(type: 'submit', name: 'submit')
    }
}

include '/WEB-INF/includes/footer.gtpl'
