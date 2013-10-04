grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        mavenRepo "http://thog.nerderg.com:8070/nexus/content/groups/public/"
        grailsCentral()
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        compile 'com.wordnik:swagger-jaxrs_2.10:1.3.0'
        compile 'com.fasterxml.jackson.core:jackson-core:2.1.0'
        compile 'com.fasterxml.jackson.core:jackson-databind:2.1.0'
        // runtime 'mysql:mysql-connector-java:5.1.21'
    }

    plugins {
        build(":tomcat:$grailsVersion",
              ":release:2.2.1",
              ":rest-client-builder:1.0.3") {
            export = false
        }

        compile ':jaxrs:0.8'
    }
}
