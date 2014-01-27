import grails.util.Environment

import org.codehaus.groovy.grails.commons.GrailsApplication

import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import com.wordnik.swagger.jaxrs.config.BeanConfig

class Swagger4jaxrsGrailsPlugin {
    def version = "0.2-SNAPSHOT"
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
        [ name: "Angel Ruiz", email: "aruizca@gmail.com" ],
        [ name: "Aaron Brown", email: "brown.aaron.lloyd@gmail.com"],
    ]
    def issueManagement = [ system: "GitHub", url: "https://github.com/nerdErg/swagger4jaxrs/issues" ]
    def scm = [ url: "https://github.com/nerdErg/swagger4jaxrs" ]

    def dependsOn = [
        jaxrs: "0.8 > *"
    ]

    def loadAfter = [
        "jaxrs"
    ]

    def doWithSpring = {
        mergeConfig(application)

        ConfigObject local = application.config.'swagger4jaxrs'

        validateLocalConfig(local)

        swaggerConfig(BeanConfig) { bean ->
            bean.autowire = true
            resourcePackage = local.resourcePackage
            version = local.version ?: "1"
            basePath = "${application.config.grails.serverURL}/api"
            title = local.title ?: grails.util.Metadata.current.'app.name'
            description = local.description ?: ""
            contact = local.contact ?: ""
            license = local.license ?: ""
            licenseUrl = local.licenseUrl ?: ""
            scan = local.scan ?: true
        }
    }

    def onConfigChange = { event ->
        mergeConfig(application)

        ConfigObject local = application.config.'swagger4jaxrs'

        validateLocalConfig(local)

        event.ctx.getBean('swaggerConfig').with {
            resourcePackage = local.resourcePackage
            version = local.version ?: "1"
            basePath = "${application.config.grails.serverURL}/api"
            title = local.title ?: grails.util.Metadata.current.'app.name'
            description = local.description ?: ""
            contact = local.contact ?: ""
            license = local.license ?: ""
            licenseUrl = local.licenseUrl ?: ""
            scan = local.scan ?: true
        }
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

    private void validateLocalConfig(ConfigObject local) {
        if (local.isEmpty()) {
            throw new IllegalStateException("The swagger4jaxrs config is missing.")
        }

        if (local.resourcePackage.isEmpty()) {
            throw new IllegalStateException("The swagger4jaxrs config requires a resourcePackage path.")
        }
    }
}
