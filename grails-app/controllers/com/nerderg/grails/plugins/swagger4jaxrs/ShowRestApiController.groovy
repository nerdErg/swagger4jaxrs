package com.nerderg.grails.plugins.swagger4jaxrs

import com.wordnik.swagger.jaxrs.config.BeanConfig

import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class ShowRestApiController {

    BeanConfig swaggerConfig
    LinkGenerator grailsLinkGenerator

    def index() {
        String basePath = grailsLinkGenerator.serverBaseURL

        swaggerConfig.basePath = basePath

        render(view: 'index', model: [apiDocsPath: "${basePath}/api-docs"])
    }
}
