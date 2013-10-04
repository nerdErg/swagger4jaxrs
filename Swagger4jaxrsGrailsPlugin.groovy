import com.wordnik.swagger.jaxrs.config.BeanConfig
import grails.util.Environment
import org.codehaus.groovy.grails.commons.GrailsApplication

class Swagger4jaxrsGrailsPlugin {
    // the plugin group
    def groupId = 'com.nerderg.grails.plugins'
    // the plugin version
    def version = "0.1-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.2 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Swagger for JAX-RS Plugin" // Headline display name of the plugin
    def author = "Angel Ruiz"
    def authorEmail = "aruizca@gmail.com"
    def description = '''\
This is a Grails plugin that adds Swagger support to document REST APIs of any Grails projects that use the Grails JAX-RS (JSR 311) plugin.
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/nerdErg/swagger4jaxrs.git"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "nerdErg Pty. Ltd.", url: "http://www.nerderg.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "GitHub", url: "https://github.com/nerdErg/swagger4jaxrs/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/nerdErg/swagger4jaxrs.git" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        mergeConfig(application)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        this.mergeConfig(application)
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }

    /**
     * Merges plugin config with host app config, but allowing customization
     * @param app
     */
    private void mergeConfig(GrailsApplication app) {
        ConfigObject currentConfig = app.config.org.grails.jaxrs
        ConfigSlurper slurper = new ConfigSlurper(Environment.getCurrent().getName());
        ConfigObject secondaryConfig = slurper.parse(app.classLoader.loadClass("SwaggerConfig"))

        ConfigObject config = new ConfigObject();
        config.putAll(secondaryConfig.org.grails.jaxrs.merge(currentConfig))

        app.config.org.grails.jaxrs = config;
    }
}
