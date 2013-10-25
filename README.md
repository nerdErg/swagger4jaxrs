![nerdErg Home](http://i.imgur.com/ulpgnsy.png)

----------

[swagger4jaxrs](http://grails.org/plugin/swagger4jaxrs)
=======================================================

This is a Grails plugin that adds [Swagger](https://developers.helloreverb.com/swagger/) support to document REST APIs of any Grails projects that use the [Grails JAX-RS (JSR 311) plugin](http://grails.org/plugin/jaxrs).

## Installation
First add the following plugin dependency to your BuildConfig.groovy file:

```groovy
compile ":swagger4jaxrs:0.1"
```

## Configuration
Next we need to configure Swagger in our host application. To do that, we have to add and customize the following Spring Bean declaration within your **resources.groovy** file located at **grails-app/conf/spring/resources.groovy**:

```groovy
    import com.wordnik.swagger.jaxrs.config.BeanConfig
    beans = {

        swaggerConfig(BeanConfig) {
            resourcePackage = "<package with your resources>"
            version = "<your REST API version>"
            basePath = grailsApplication.config.grails.serverURL
            title = "<your App Name>"
            description = "<your description here>"
            contact = "<your email>"
            license = "<your license>"
            licenseUrl = "<your license link>"
            scan = true
        }
    }
```

If you are updating from a previous version of swagger4jaxrs and want to use the above method to configure your app you will need to remove the bean declaration from applicationContext.xml that you had previously added.


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
@Api(value="/user", description = "Operations about user")
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
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid user supplied"),
			@ApiResponse(code = 404, message = "User not found") })
	public Response updateUser(
			@ApiParam(value = "name that need to be deleted", required = true) @PathParam("username") String username,
			@ApiParam(value = "Updated user object", required = true) User user) {
		userData.addUser(user);
		return Response.ok().entity("").build();
	}

	@DELETE
	@Path("/{username}")
	@ApiOperation(value = "Delete user", notes = "This can only be done by the logged in user.")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid username supplied"),
			@ApiResponse(code = 404, message = "User not found") })
	public Response deleteUser(
			@ApiParam(value = "The name that needs to be deleted", required = true) @PathParam("username") String username) {
		userData.removeUser(username);
		return Response.ok().entity("").build();
	}

	@GET
	@Path("/{username}")
	@ApiOperation(value = "Get user by user name", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid username supplied"),
			@ApiResponse(code = 404, message = "User not found") })
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
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid username/password supplied") })
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


