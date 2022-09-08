package io.github.kahar.repo;

import io.github.kahar.model.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostEntityGraphRepository {
    @EntityGraph(value = "post-entity-graph-with-comment-users", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "SELECT p FROM Post p where p.id = :id")
    Post getByIdEagerWithEntityGraph(@Param("id") Long id);

    @EntityGraph(value = "post-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "SELECT p FROM Post p where p.id = :id")
    Post getByIdLazyWithEntityGraph(@Param("id") Long id);

    @Query(value = """
            SELECT p FROM Post p
            left join fetch p.comments
            left join fetch p.user
            left join fetch p.comments.user
            where p.id = :id""")
    Post getByIdEager(@Param("id") Long id);

    @Query(value = "SELECT p FROM Post p where p.id = :id")
    Post getByIdLazy(@Param("id") Long id);
}