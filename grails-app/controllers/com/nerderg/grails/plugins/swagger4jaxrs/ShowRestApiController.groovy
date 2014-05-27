package com.nerderg.grails.plugins.swagger4jaxrs

import com.wordnik.swagger.jaxrs.config.BeanConfig

class ShowRestApiController {

    BeanConfig swaggerConfig

    def index() {
        swaggerConfig.basePath = request.contextPath

        render(view: 'index', model: [apiDocsPath: "${request.contextPath}/api-docs"])
    }
}
