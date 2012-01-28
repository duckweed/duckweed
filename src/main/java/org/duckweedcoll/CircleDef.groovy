package org.duckweedcoll

import groovyx.gaelyk.GaelykBindings

@GaelykBindings
class CircleDef {
    def name
    def description
    def members

    def getSecretary() {
        if (members == null) {
            'unknown'
        } else {
            datastore.get(members).getProperty('user')
        }
    }

    def id
}
