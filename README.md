[![nerdErg home](http://i.imgur.com/vNBBHNW.png)](http://www.nerderg.com/)

----------

[swagger4jaxrs](http://grails.org/plugin/swagger4jaxrs)
=======================================================

This is a Grails plugin that adds [Swagger](https://developers.helloreverb.com/swagger/) support to document REST APIs of any Grails projects that use the [Grails JAX-RS (JSR 311) plugin](http://grails.org/plugin/jaxrs).

## Changelog

* **version 0.2**: 
 * The recommmended way to configure this plugin is using the Config.groovy files instead of the resources.groovy file. Lookk at the documentation bellow. This enables hot-swap when the configuration parameters change.
 * Ugraded **com.wordnick:swagger-jaxrs_2.10** dependency to version **1.3.2** from 1.3.0
 * Upgraded [**swagger-ui**](https://github.com/wordnik/swagger-ui) client code to version **2.0.14** from 2.0.2
 * Compatible with Grails 2.3 when using jaxrs:0.10
 * Updated documentation.
* **version 0.1**:
 * Initial release.


## Installation
First add the following plugin dependency to your BuildConfig.groovy file:

```groovy
compile ":swagger4jaxrs:0.1"
```

By default this plugin depends on the jaxrs plugin **version 0.8**. That version of jaxrs is **only compatible with Grails 2.2**. If you are using a different version of Grails you will have to manually add the jaxrs plugin dependecy:

* If Grails version <= 2.2

```
    compile ":jaxrs:0.9"
```
* If Grails version >= 2.3

```
    compile ":jaxrs:0.10"
```  

## Configuration

The following configuration is the minimum the plugin requires, which can be placed in the ```grails-app/conf/Config.groovy``` file:

```groovy
'swagger4jaxrs' {
    resourcePackage = '<package with your resources>'
}
```

And this is the fully enumerated setup:

```groovy
'swagger4jaxrs' {
    resourcePackage = '<package with your resources>'
	basePath = "${grailsApplication.config.grails.serverURL}/api" ?: ''
    version = '<your REST API version>' // Default "1".
    title = '<your desired title>' // Default: App Name.
    description = '<your description here>'
    contact = '<your email>'
    license = '<your license>'
    licenseUrl = '<your license link>'
    scan = true
}
```

Make sure you have added the Swagger annotations in your JAX-RS "resources" with the required meta information to generate a comprehensive documentarion for you REST API. Next there is a good [example](https://github.com/wordnik/swagger-core/tree/master/samples "Swager implementation samples") to show you how:

```java
/**
 *  Copyright 2012 Wordnik, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wordnik.swagger.sample.resource;

import com.wordnik.swagger.annotations.*;
import com.wordnik.swagger.sample.data.UserData;
import com.wordnik.swagger.sample.model.User;
import com.wordnik.swagger.sample.exception.ApiException;
import com.wordnik.swagger.sample.exception.NotFoundException;


import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/api/user")
@Api(value="/api/user", description = "Operations about user")
@Produces({"application/json"})
public class UserResource {
	static UserData userData = new UserData();

	@POST
	@ApiOperation(value = "Create user", notes = "This can only be done by the logged in user.")
	public Response createUser(
			@ApiParam(value = "Created user object", required = true) User user) {
		userData.addUser(user);
		return Response.ok().entity("").build();
	}

  @POST
  @Path("/createWithArray")
  @ApiOperation(value = "Creates list of users with given input array")
  public Response createUsersWithArrayInput(@ApiParam(value = "List of user object", required = true) User[] users) {
      for (User user : users) {
          userData.addUser(user);
      }
      return Response.ok().entity("").build();
  }

  @POST
  @Path("/createWithList")
  @ApiOperation(value = "Creates list of users with given input array")
  public Response createUsersWithListInput(@ApiParam(value = "List of user object", required = true) java.util.List<User> users) {
      for (User user : users) {
          userData.addUser(user);
      }
      return Response.ok().entity("").build();
  }

	@PUT
	@Path("/{username}")
	@ApiOperation(value = "Updated user", notes = "This can only be done by the logged in user.")
	@ApiResponses(value = [
			@ApiResponse(code = 400, message = "Invalid user supplied"),
			@ApiResponse(code = 404, message = "User not found") ])
	public Response updateUser(
			@ApiParam(value = "name that need to be deleted", required = true) @PathParam("username") String username,
			@ApiParam(value = "Updated user object", required = true) User user) {
		userData.addUser(user);
		return Response.ok().entity("").build();
	}

	@DELETE
	@Path("/{username}")
	@ApiOperation(value = "Delete user", notes = "This can only be done by the logged in user.")
	@ApiResponses(value = [
			@ApiResponse(code = 400, message = "Invalid username supplied"),
			@ApiResponse(code = 404, message = "User not found") ])
	public Response deleteUser(
			@ApiParam(value = "The name that needs to be deleted", required = true) @PathParam("username") String username) {
		userData.removeUser(username);
		return Response.ok().entity("").build();
	}

	@GET
	@Path("/{username}")
	@ApiOperation(value = "Get user by user name", response = User.class)
	@ApiResponses(value = [
			@ApiResponse(code = 400, message = "Invalid username supplied"),
			@ApiResponse(code = 404, message = "User not found") ])
	public Response getUserByName(
			@ApiParam(value = "The name that needs to be fetched. Use user1 for testing. ", required = true) @PathParam("username") String username)
		throws ApiException {
		User user = userData.findUserByName(username);
		if (null != user) {
			return Response.ok().entity(user).build();
		} else {
			throw new NotFoundException(404, "User not found");
		}
	}

	@GET
	@Path("/login")
	@ApiOperation(value = "Logs user into the system", response = String.class)
	@ApiResponses(value = [ @ApiResponse(code = 400, message = "Invalid username/password supplied") ])
	public Response loginUser(
			@ApiParam(value = "The user name for login", required = true) @QueryParam("username") String username,
			@ApiParam(value = "The password for login in clear text", required = true) @QueryParam("password") String password) {
		return Response.ok()
				.entity("logged in user session:" + System.currentTimeMillis())
				.build();
	}

	@GET
	@Path("/logout")
	@ApiOperation(value = "Logs out current logged in user session")
	public Response logoutUser() {
		return Response.ok().entity("").build();
	}
}

```

## Checking that everything is working
First of all, after running your app, please check that your [Swagger API declaration](https://github.com/wordnik/swagger-core/wiki/API-Declaration) is indeed available by using the following url:

```
http://localhost:<port>/<host-app-context>/api-docs
```

If you are get the JSON with your API declaration, tehn we are ready to see if ca display it in the [Swagger-UI](https://github.com/wordnik/swagger-ui) way trying this url in your browser:

```
http://localhost:<port>/<host-app-context>/showRestApi
```

or if you are using ["hyphenated" URL convention](http://grails.org/doc/2.2.x/guide/theWebLayer.html#customizingUrlFormat):

```
http://localhost:<port>/<host-app-context>/show-rest-api
```

### and then you should be getting something as beautiful as the following screenshot :-):

![Swagger REST API representation](http://i.imgur.com/y7ar45l.png)


