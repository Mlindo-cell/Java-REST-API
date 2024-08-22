package com.mlindo.Springsuit.services;


import com.mlindo.Springsuit.models.Comment;
import com.mlindo.Springsuit.repositories.CommentRepository;
import java.util.List;

public class CommentService {
    private CommentRepository commentRepository = new CommentRepository();

    public List<Comment> getAllComments() {
        return commentRepository.getAllComments();
    }

    public Comment getCommentById(int id) {
        return commentRepository.getCommentById(id);
    }

    public void createComment(Comment comment) {
        commentRepository.createComment(comment);
    }

    public void updateComment(Comment comment) {
        commentRepository.updateComment(comment);
    }

    public void deleteComment(int id) {
        commentRepository.deleteComment(id);
    }
}
