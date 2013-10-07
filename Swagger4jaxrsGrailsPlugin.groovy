import grails.util.Environment

import org.codehaus.groovy.grails.commons.GrailsApplication

class Swagger4jaxrsGrailsPlugin {
    def groupId = 'com.nerderg.grails.plugins'
    def version = "0.1"
    def grailsVersion = "2.0 > *"
    def pluginExcludes = [
        "web-app/WEB-INF/**"
    ]
    def title = "Swagger for JAX-RS Plugin"
    def description = 'Adds Swagger support to document REST APIs of projects that use the JAX-RS (JSR 311) plugin'

    def documentation = "https://github.com/nerdErg/swagger4jaxrs"
    def license = "APACHE"
    def organization = [ name: "nerdErg Pty. Ltd.", url: "http://www.nerderg.com/" ]
    def developers = [
        [ name: "Angel Ruiz", email: "aruizca@gmail.com" ]
    ]
    def issueManagement = [ system: "GitHub", url: "https://github.com/nerdErg/swagger4jaxrs/issues" ]
    def scm = [ url: "https://github.com/nerdErg/swagger4jaxrs" ]

    def doWithSpring = {
        mergeConfig(application)
    }

    def onConfigChange = { event ->
        mergeConfig(application)
    }

    /**
     * Merges plugin config with host app config, but allowing customization
     * @param app
     */
    private void mergeConfig(GrailsApplication app) {
        ConfigObject currentConfig = app.config.org.grails.jaxrs
        ConfigSlurper slurper = new ConfigSlurper(Environment.current.name)
        ConfigObject secondaryConfig = slurper.parse(app.classLoader.loadClass("SwaggerConfig"))

        ConfigObject config = new ConfigObject()
        config.putAll(secondaryConfig.org.grails.jaxrs.merge(currentConfig))

        app.config.org.grails.jaxrs = config
    }
}
