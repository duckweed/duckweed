Duckweed Collaboration
======================

To run locally:

mvn package gae:run

To deploy on google app:

mvn package gae:deploy

To run integration tests:

in one window run -
mvn gae:run

and in another run -
mvn verify

to upload site:
mvn site -Dgithub.global.userName=duckweed -Dgithub.global.password=**********

to live deploy new code on a running local instance, run this in a separate window:
mvn compile war:exploded