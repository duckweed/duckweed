package org.duckweedcoll

import groovyx.gaelyk.GaelykBindings

@GaelykBindings
class Circle {
    def name
    def description
    def members

    def getSecretary() {
        if (members == null) {
            'unknown'
        } else {
            datastore.get(members).getProperty('username')
        }
    }

    def id
}
