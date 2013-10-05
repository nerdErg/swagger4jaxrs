<!DOCTYPE html>
<html>
<head>
  <title>Swagger UI</title>
  <link href='//fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet' type='text/css'/>
  <link href='${resource(dir: 'vendor/swagger-ui/2.0.2/css', file: 'highlight.default.css')}' media='screen' rel='stylesheet' type='text/css'/>
  <link href='${resource(dir: 'vendor/swagger-ui/2.0.2/css', file: 'screen.css')}' media='screen' rel='stylesheet' type='text/css'/>
  
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'shred.bundle.js')}"></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'jquery-1.8.0.min.js')}"></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'jquery.slideto.min.js')}" ></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'jquery.wiggle.min.js')}" ></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'jquery.ba-bbq.min.js')}" ></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'handlebars-1.0.0.js')}" ></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'underscore-min.js')}"></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'backbone-min.js')}"></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'swagger.js')}"></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2', file: 'swagger-ui.js')}"></script>
  <script src="${resource(dir: 'vendor/swagger-ui/2.0.2/lib', file: 'highlight.7.3.pack.js')}" ></script>
  <script >
    var baseUrl = '';
    $(function () {
      window.swaggerUi = new SwaggerUi({
        url: "${grailsApplication.config.grails.serverURL}/api-docs",
        dom_id: "swagger-ui-container",
        supportedSubmitMethods: ['get', 'post', 'put', 'delete'],
        onComplete: function (swaggerApi, swaggerUi) {
          if (console) {
            console.log("Loaded SwaggerUI")
          }
          $('pre code').each(function (i, e) {
            hljs.highlightBlock(e)
          });
        },
        onFailure: function (data) {
          if (console) {
            console.log("Unable to Load SwaggerUI");
            console.log(data);
          }
        },
        docExpansion: "none"
      });

      $('#input_apiKey').change(function () {
        var key = $('#input_apiKey')[0].value;
        console.log("key: " + key);
        if (key && key.trim() != "") {
          console.log("added key " + key);
          window.authorizations.add("key", new ApiKeyAuthorization("api_key", key, "query"));
        }
      })
      window.swaggerUi.load();
    });

  </script>
</head>

<body>
<div id='header'>
  <div class="swagger-ui-wrap">
    <a id="logo" href="https://developers.helloreverb.com/swagger/">swagger</a>

    <form id='api_selector'>

      <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl" type="text"/>
      </div>

      <div class='input'><input placeholder="api_key" id="input_apiKey" name="apiKey" type="text"/></div>

      <div class='input'><a id="explore" href="#">Explore</a></div>
    </form>
  </div>
</div>

<div id="message-bar" class="swagger-ui-wrap">
  &nbsp;
</div>

<div id="swagger-ui-container" class="swagger-ui-wrap">

</div>

</body>

</html>
