
include '/WEB-INF/includes/header.gtpl'

html.html {
    h1 'New User Details'
    form(action: 'profile.groovy') {
        p('Nick Name')
        input(type: 'text', name: 'username') { request.session.person.username }
        p('Bio')
        input(type: 'text', name: 'bio') { request.session.person.bio }
        input(type:'submit', name:'submit')
    }
}

include '/WEB-INF/includes/footer.gtpl'
