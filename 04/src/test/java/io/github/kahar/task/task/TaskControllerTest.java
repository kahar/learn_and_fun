package io.github.kahar.task.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;
import io.github.kahar.task.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskControllerTest extends AbstractControllerTest {
    private final Task taskFirst = Task.builder().id(-1L).title("Title 1").build();
    private final Task taskSecond = Task.builder().id(-1L).title("Title 2").build();
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskTestHelper helper;

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        helper.setup(serverPort, restTemplate);
    }

    @BeforeEach
    public void dbClean() {
        taskRepository.deleteAll();
    }

    @Test
    public void getEmpty() {
        Task[] restArrayResult = helper.getTasks();
        assertThat(restArrayResult).isEmpty();
    }

    @Test
    public void createOneTask() {
        helper.createTasks(taskFirst);
        helper.assertThatTaskResultContainsOnly(taskFirst);
    }

    @Test
    public void createTwoTask() {
        helper.createTasks(taskFirst, taskSecond);
        helper.assertThatTaskResultContainsOnly(taskFirst, taskSecond);
    }

    @Test
    public void updateTaskWhenTwoTaskAreCreated() {
        helper.createTasks(taskFirst, taskSecond);

        Task taskFirstCreated = Arrays.stream(helper.getTasks())
                .filter(e -> e.getTitle()
                        .equals(taskFirst.getTitle()))
                .findFirst()
                .orElseThrow();
        Task taskFirstUpdated = Task.builder().id(taskFirstCreated.getId()).title(taskFirstCreated.getTitle()).build();
        taskFirstUpdated.setId(taskFirst.getId());
        taskFirstUpdated.setTitle(taskFirstUpdated.getTitle() + "Hakuna matata");
        JsonPatch patch = JsonDiff.asJsonPatch(mapper.valueToTree(taskFirst), mapper.valueToTree(taskFirstUpdated));
        restTemplate.patchForObject(helper.getUri() + "/" + taskFirstCreated.getId(), patch, Task.class);
        helper.assertThatTaskResultContainsOnly(taskFirstUpdated, taskSecond);
    }

    @Test
    public void createTwoTasksAndRemoveBoth() {
        helper.createTasks(taskFirst, taskSecond);

        Task[] tasks = helper.getTasks();

        Task taskFirstCreated = Arrays.stream(tasks)
                .filter(e -> e.getTitle()
                        .equals(taskFirst.getTitle()))
                .findFirst()
                .orElseThrow();
        restTemplate.delete(helper.getUri() + "/" + taskFirstCreated.getId());

        Task taskSecondCreated = Arrays.stream(tasks)
                .filter(e -> e.getTitle()
                        .equals(taskSecond.getTitle()))
                .findFirst()
                .orElseThrow();

        restTemplate.delete(helper.getUri() + "/" + taskSecondCreated.getId());

        helper.assertThatTaskResultIsEmpty();
    }

    @Test
    public void createOneTaskAndRemove() {
        helper.createTasks(taskFirst);

        Task taskFirstCreated = Arrays.stream(helper.getTasks())
                .filter(e -> e.getTitle()
                        .equals(taskFirst.getTitle()))
                .findFirst()
                .orElseThrow();
        restTemplate.delete(helper.getUri() + "/" + taskFirstCreated.getId());
        helper.assertThatTaskResultIsEmpty();
    }
}
