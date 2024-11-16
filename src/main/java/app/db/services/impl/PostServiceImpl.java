package app.db.services.impl;

import app.db.entities.Post;
import app.db.reposotories.PostRepository;
import app.db.services.interfaces.PostService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Getter
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return (List<Post>)repository.findAll();
    }

    @Override
    public void save(Post post) {
        if (post != null) {
            postRepository.save(post);
        }
    }

    @Override
    public void update(Post post) {
        if (post != null) {
            postRepository.save(post);
        }
    }

    @Override
    public void delete(Post post) {
        if (post != null && postRepository.existsById((long) Math.toIntExact(post.getId()))) {
            postRepository.deleteById((long) Math.toIntExact(post.getId()));
        }
    }

    @Override
    public void deleteById(long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        }
    }
}
