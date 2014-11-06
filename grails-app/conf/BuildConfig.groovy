grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        mavenRepo "http://maven.restlet.org/"
    }

    dependencies {
        compile 'com.wordnik:swagger-jaxrs_2.10:1.3.10'
        compile 'com.fasterxml.jackson.core:jackson-core:2.4.3'
        compile 'com.fasterxml.jackson.core:jackson-databind:2.4.3'

        if (owner.grailsVersion ==~ /2\.2\..*/) {
            test('org.spockframework:spock-grails-support:0.7-groovy-2.0') {
                export = false
            }
        }
    }

    plugins {
        compile(':jaxrs:0.10')

        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }

        test(':spock:0.7') {
            if (owner.grailsVersion ==~ /2\.2\..*/) {
                exclude "spock-grails-support"
            }
            export = false
        }
    }


}

