package org.duckweedcoll

import com.google.appengine.api.datastore.Entity
import groovyx.gaelyk.GaelykBindings
import com.google.appengine.api.datastore.Email

/*
      Licensed to the Apache Software Foundation (ASF) under one
      or more contributor license agreements.  See the NOTICE file
      distributed with this work for additional information
      regarding copyright ownership.  The ASF licenses this file
      to you under the Apache License, Version 2.0 (the
      "License"); you may not use this file except in compliance
      with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing,
      software distributed under the License is distributed on an
      "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
      KIND, either express or implied.  See the License for the
      specific language governing permissions and limitations
      under the License.
*/
@GaelykBindings
class UserCircle {

    Entity createUser(String name, String address, String email) {
        def entity = new Entity('user')
        entity.setProperty 'email', new Email(email)
        entity.setProperty 'address', address
        entity.setProperty 'name', name
        entity
    }


    Entity createCircle(String circleName, Entity initialOwner) {
        Entity circle = new Entity('circle')
        circle.setProperty 'name', circleName
        circle.setProperty 'owner', initialOwner.getKey()

        initialOwner.setProperty('circles', circle.getKey())
        datastore.put initialOwner
        return circle
    }

    Entity addCircleToUser(Entity user1, Entity circle) {
        def circles = user1.getProperty('circles')
        user1.setProperty 'circles', [] + circles + circle.getKey()
    }

    Entity addOwnerToCircle(Entity circle2, Entity user2) {
        def owners = circle2.getProperty('owner')
        circle2.setProperty 'owner', [] + owners + user2.getKey()
    }

}
