package com.mlindo.Springsuit.resources;

import com.mlindo.Springsuit.models.Post;
import com.mlindo.Springsuit.services.PostService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/posts")
public class PostResource {
    private static final Logger LOGGER = Logger.getLogger(PostResource.class.getName());
    private PostService postService = new PostService();

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public Response getPosts() {
        List<Post> posts = postService.getAllPosts();
        return Response.ok(posts).build();
    }

    @GET
    @Path("/{id}")
    @Produces( MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("id") int id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            LOGGER.warning("Post with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Post with ID " + id + " not found").build();
        }
        return Response.ok(post).build();
    }

    @POST
    @Consumes( MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response createPost(Post post) {
        postService.createPost(post);
        LOGGER.info("Created new post with ID: " + post.getId());
        return Response.status(Response.Status.CREATED).entity(post).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes( MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response updatePost(@PathParam("id") int id, Post post) {
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            LOGGER.warning("Attempted to update non-existent post with ID: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Post with ID " + id + " not found").build();
        }
        post.setId(id);
        postService.updatePost(post);
        LOGGER.info("Updated post with ID: " + id);
        return Response.ok(post).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePost(@PathParam("id") int id) {
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            LOGGER.warning("Attempted to delete non-existent post with ID: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Post with ID " + id + " not found").build();
        }
        postService.deletePost(id);
        LOGGER.info("Deleted post with ID: " + id);
        return Response.noContent().build();
    }
}

