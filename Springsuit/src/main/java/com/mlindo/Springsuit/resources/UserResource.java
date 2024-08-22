package com.mlindo.Springsuit.resources;

import com.mlindo.Springsuit.models.User;
import com.mlindo.Springsuit.services.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/users")
public class UserResource {
    private static final Logger LOGGER = Logger.getLogger(UserResource.class.getName());
    private UserService userService = new UserService();

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = userService.getAllUsers();
        LOGGER.info("Fetched all users");
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    @Produces( MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            LOGGER.warning("User with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("User with ID " + id + " not found").build();
        }
        LOGGER.info("Fetched user with ID: " + id);
        return Response.ok(user).build();
    }

    @POST
    @Consumes( MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        userService.createUser(user);
        LOGGER.info("Created new user with ID: " + user.getId());
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes( MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            LOGGER.warning("Attempted to update non-existent user with ID: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("User with ID " + id + " not found").build();
        }
        user.setId(id);
        userService.updateUser(user);
        LOGGER.info("Updated user with ID: " + id);
        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            LOGGER.warning("Attempted to delete non-existent user with ID: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("User with ID " + id + " not found").build();
        }
        userService.deleteUser(id);
        LOGGER.info("Deleted user with ID: " + id);
        return Response.noContent().build();
    }
}
