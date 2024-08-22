package com.mlindo.Springsuit.resources;

import com.mlindo.Springsuit.models.Comment;
import com.mlindo.Springsuit.services.CommentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/comments")
public class CommentResource {
    private static final Logger LOGGER = Logger.getLogger(CommentResource.class.getName());
    private CommentService commentService = new CommentService();

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public Response getComments() {
        List<Comment> comments = commentService.getAllComments();
        return Response.ok(comments).build();
    }

    @GET
    @Path("/{id}")
    @Produces( MediaType.APPLICATION_JSON)
    public Response getComment(@PathParam("id") int id) {
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Comment with ID " + id + " not found").build();
        }
        return Response.ok(comment).build();
    }

    @POST
    @Consumes( MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response createComment(Comment comment) {
        commentService.createComment(comment);
        return Response.status(Response.Status.CREATED).entity(comment).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes( MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response updateComment(@PathParam("id") int id, Comment comment) {
        Comment existingComment = commentService.getCommentById(id);
        if (existingComment == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Comment with ID " + id + " not found").build();
        }
        comment.setId(id);
        commentService.updateComment(comment);
        return Response.ok(comment).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(@PathParam("id") int id) {
        Comment existingComment = commentService.getCommentById(id);
        if (existingComment == null) {
            LOGGER.warning("Attempted to delete non-existent comment with ID: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Comment with ID " + id + " not found").build();
        }
        commentService.deleteComment(id);
        LOGGER.info("Deleted comment with ID: " + id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

