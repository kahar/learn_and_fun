package io.github.kahar.repo;

import io.github.kahar.model.Post;

public interface PostEntityGraphRepository {
    Post find(Long id);

    Post findWithEntityGraph(Long id);

    Post findWithEntityGraph2(Long id);
}
