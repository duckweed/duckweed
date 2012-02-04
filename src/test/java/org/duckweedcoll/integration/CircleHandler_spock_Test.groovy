import org.duckweedcoll.spock.GaelykUnitSpec

class CircleHandler_spock_Test extends GaelykUnitSpec{
    def setup(){
        groovlet 'bl/CircleHandler.groovy'
    }

    def "find the handler"(){
        given:
        CircleHandler.get()
    }

}