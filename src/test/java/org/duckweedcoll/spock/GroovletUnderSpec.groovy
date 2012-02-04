package org.duckweedcoll.spock

class GroovletUnderSpec {

    private static final String testRoot = 'src/main/webapp/WEB-INF/groovy'
    def gse = new GroovyScriptEngine(testRoot)
    def binding = new Binding()
    def log = new GroovletMockLogger(level: 'info')
    def scriptName

    def forward = ''
    def redirect = ''
    def logging = ''

    GroovletUnderSpec(script) {
        validateScript(script)
        this.scriptName = script
        bindVariables()
    }

    void validateScript(script) {
        if (!script) {
            throw new IllegalStateException('The scriptName was not defined in setup()')
        }
        final file = new File("$testRoot/$script")
        if (!file.exists()) {
            throw new IllegalArgumentException("${file.canonicalPath} does not exist")
        }
    }

    def bindVariables = {
        binding.setVariable 'params', [:]
        binding.setVariable 'headers', [:]
        binding.setVariable 'request', [:]
        binding.setVariable 'forward', { forward = it }
        binding.setVariable 'redirect', { redirect = it }
        binding.setVariable 'log', log
    }

    void get() {
        run()
    }

    void post() {
        run()
    }

    void run() {
        gse.run scriptName, binding
        logging = this.log.buffer
        println logging
    }

    def propertyMissing(String name, value) {
        log.config "Adding $name:$value to the $scriptName binding."
        binding.setVariable name, value
    }

    def propertyMissing(String name) {
        binding.getVariable name
    }

}
