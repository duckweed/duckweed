include '/WEB-INF/includes/header.gtpl'


html.html {
    h1 'about'
}
include '/WEB-INF/includes/person.gtpl'


html.html {
    table {
        tr {
            th('property')
            th('value')
        }
        tr {
            td('Application Id')
            td {
                mkp.yield app.id
            }
        }
        tr {
            td('Application Version')
            td {
                mkp.yield app.version
            }
        }
        tr {
            td('Gaelyk Version')
            td {
                mkp.yield app.gaelyk.version
            }
        }
        tr {
            td('Environment Name')
            td {
                mkp.yield app.env.name
            }
        }
        tr {
            td('Environment Version')
            td {
                mkp.yield app.env.version
            }
        }
    }
}

include '/WEB-INF/includes/footer.gtpl'
