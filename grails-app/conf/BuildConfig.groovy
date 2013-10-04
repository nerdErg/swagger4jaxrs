grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        mavenRepo "http://thog.nerderg.com:8070/nexus/content/groups/public/"
        mavenRepo "http://maven.restlet.org/"
    }

    dependencies {
        compile 'com.wordnik:swagger-jaxrs_2.10:1.3.0'
        compile 'com.fasterxml.jackson.core:jackson-core:2.1.0'
        compile 'com.fasterxml.jackson.core:jackson-databind:2.1.0'
    }

    plugins {
        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }

        compile ':jaxrs:0.8'
    }
}
