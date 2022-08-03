package io.github.kahar.repo;

import io.github.kahar.model.Post;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Component
@Service
public class PostEntityGraphRepositoryImpl implements PostEntityGraphRepository {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public Post find(Long id) {
        EntityManager entityManager = emf.createEntityManager();

        Post post = entityManager.find(Post.class, id);

        entityManager.close();
        return post;
    }

    @Override
    public Post findWithEntityGraph(Long id) {
        EntityManager entityManager = emf.createEntityManager();

        EntityGraph entityGraph = entityManager.getEntityGraph("post-entity-graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        Post post = entityManager.find(Post.class, id, properties);

        entityManager.close();
        return post;
    }

    @Override
    public Post findWithEntityGraph2(Long id) {
        EntityManager entityManager = emf.createEntityManager();

        EntityGraph<Post> entityGraph = entityManager.createEntityGraph(Post.class);
        entityGraph.addAttributeNodes("subject");
        entityGraph.addAttributeNodes("user");
        entityGraph.addSubgraph("comments")
                .addAttributeNodes("user");

        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        Post post = entityManager.find(Post.class, id, properties);

        entityManager.close();
        return post;
    }
}