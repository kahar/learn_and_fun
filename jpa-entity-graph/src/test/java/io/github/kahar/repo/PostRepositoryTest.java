package io.github.kahar.repo;

import io.github.kahar.JpaEntityGraphApplication;
import io.github.kahar.model.Comment;
import io.github.kahar.model.Post;
import io.github.kahar.model.User;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JpaEntityGraphApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void find() {
        Assertions.assertThrows(LazyInitializationException.class, () -> {
            Post post = postRepository.find(1L);
            assertNotNull(post.getUser());
            String email = post.getUser().getEmail();
            assertNull(email);
        });
    }

    @Test
    public void findThrowsLazyInitializationException() {
        Assertions.assertThrows(LazyInitializationException.class, () -> {
            Post post = postRepository.find(1L);
            assertNotNull(post.getUser());
            String email = post.getUser().getEmail();
            assertNotNull(email);
            assertNotNull(post.getComments());
            assertEquals(post.getComments().size(), 2);
            Comment comment = post.getComments().get(0);
            assertNotNull(comment);
            User user = comment.getUser();
            user.getEmail();
        });
    }

    @Test
    public void findById() {
        Post post = postRepository.findById(1L).get();
        assertNotNull(post);
    }

    @Test
    public void findByIdThrowsLazyInitializationException() {
        Assertions.assertThrows(LazyInitializationException.class, () -> {
            Post post = postRepository.findById(1L).get();
            assertNotNull(post.getUser());
            String email = post.getUser().getEmail();
            assertNull(email);
        });
    }

    @Test
    public void getByIdEagerWithEntityGraph() {
        Post post = postRepository.getByIdEagerWithEntityGraph(1L);
        assertNotNull(post.getComments());
        assertEquals(post.getComments().size(), 2);
        Comment comment = post.getComments().get(0);
        assertNotNull(comment);
        User user = comment.getUser();
        assertNotNull(user);
        assertEquals(user.getEmail(), "user2@test.com");
    }

    @Test
    public void getByIdLazyWithEntityGraph() {
        Post post = postRepository.getByIdLazyWithEntityGraph(1L);
        assertNotNull(post);
    }

    @Test
    public void getByIdLazyWithEntityGraphThrowsLazyInitializationException() {
        Assertions.assertThrows(LazyInitializationException.class, () -> {
            Post post = postRepository.getByIdLazyWithEntityGraph(1L);
            assertNotNull(post.getUser());
            String email = post.getUser().getEmail();
            assertNotNull(email);
            assertNotNull(post.getComments());
            assertEquals(post.getComments().size(), 2);
            Comment comment = post.getComments().get(0);
            assertNotNull(comment);
            User user = comment.getUser();
            user.getEmail();
        });
    }

    @Test
    public void getByIdEager() {
        Post post = postRepository.getByIdEager(1L);
        assertNotNull(post.getComments());
        assertEquals(post.getComments().size(), 2);
        Comment comment = post.getComments().get(0);
        assertNotNull(comment);
        User user = comment.getUser();
        assertNotNull(user);
        assertEquals(user.getEmail(), "user2@test.com");
    }

    @Test
    public void getByIdLazy() {
        Post post = postRepository.getByIdLazy(1L);
        assertNotNull(post);
    }

    @Test
    public void getByIdLazyThrowsLazyInitializationException() {
        Assertions.assertThrows(LazyInitializationException.class, () -> {
            Post post = postRepository.getByIdLazy(1L);
            assertNotNull(post.getUser());
            String email = post.getUser().getEmail();
            assertNotNull(email);
            assertNotNull(post.getComments());
            assertEquals(post.getComments().size(), 2);
            Comment comment = post.getComments().get(0);
            assertNotNull(comment);
            User user = comment.getUser();
            user.getEmail();
        });
    }

    @Test
    public void findWithEntityGraph() {
        Post post = postRepository.findWithEntityGraph(1L);
        assertNotNull(post);
    }

    @Test
    public void findWithEntityGraphThrowsLazyInitializationException() {
        Assertions.assertThrows(LazyInitializationException.class, () -> {
            Post post = postRepository.findWithEntityGraph(1L);
            assertNotNull(post.getUser());
            String email = post.getUser().getEmail();
            assertNotNull(email);
            assertNotNull(post.getComments());
            assertEquals(post.getComments().size(), 2);
            Comment comment = post.getComments().get(0);
            assertNotNull(comment);
            User user = comment.getUser();
            user.getEmail();
        });
    }

    @Test
    public void findWithEntityGraph2_Comment_With_User() {
        Post post = postRepository.findWithEntityGraph2(1L);
        assertNotNull(post.getComments());
        assertEquals(post.getComments().size(), 2);
        Comment comment = post.getComments().get(0);
        assertNotNull(comment);
        User user = comment.getUser();
        assertNotNull(user);
        assertEquals(user.getEmail(), "user2@test.com");
    }
}
