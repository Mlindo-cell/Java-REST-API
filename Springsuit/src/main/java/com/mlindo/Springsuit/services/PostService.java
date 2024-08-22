package com.mlindo.Springsuit.services;


import com.mlindo.Springsuit.models.Post;
import com.mlindo.Springsuit.repositories.PostRepository;
import java.util.List;

public class PostService {
    private PostRepository postRepository = new PostRepository();

    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    public Post getPostById(int id) {
        return postRepository.getPostById(id);
    }

    public void createPost(Post post) {
        postRepository.createPost(post);
    }

    public void updatePost(Post post) {
        postRepository.updatePost(post);
    }

    public void deletePost(int id) {
        postRepository.deletePost(id);
    }
}
