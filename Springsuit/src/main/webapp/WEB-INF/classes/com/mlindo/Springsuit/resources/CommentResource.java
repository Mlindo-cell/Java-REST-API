package com.mlindo.Springsuit.resources;

import com.mlindo.Springsuit.models.Comment;
import com.mlindo.Springsuit.services.CommentService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/comments")
public class CommentResource {
    private CommentService commentService = new CommentService();

    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response getComments() {
        List<Comment> comments = commentService.getAllComments();
        return Response.ok(comments).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response getComment(@PathParam("id") int id) {
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(comment).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response createComment(Comment comment) {
        commentService.createComment(comment);
        return Response.status(Response.Status.CREATED).entity(comment).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response updateComment(@PathParam("id") int id, Comment comment) {
        Comment existingComment = commentService.getCommentById(id);
        if (existingComment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
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
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        commentService.deleteComment(id);
        return Response.noContent().build();
    }
}
