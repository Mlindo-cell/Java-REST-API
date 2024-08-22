package com.mlindo.Springsuit.resources;


import com.mlindo.Springsuit.models.Post;
import com.mlindo.Springsuit.services.PostService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/posts")
public class PostResource {
    private PostService postService = new PostService();

    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response getPosts() {
        List<Post> posts = postService.getAllPosts();
        return Response.ok(posts).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response getPost(@PathParam("id") int id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(post).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response createPost(Post post) {
        postService.createPost(post);
        return Response.status(Response.Status.CREATED).entity(post).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response updatePost(@PathParam("id") int id, Post post) {
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        post.setId(id);
        postService.updatePost(post);
        return Response.ok(post).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePost(@PathParam("id") int id) {
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        postService.deletePost(id);
        return Response.noContent().build();
    }
}
