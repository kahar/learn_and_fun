package io.github.kahar.task.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;
import io.github.kahar.task.TaskApplication;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TaskApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TaskControllerTest {
    private final Task taskFirst = Task.builder().id(-1L).title("Title 1").build();
    private final Task taskSecond = Task.builder().id(-1L).title("Title 2").build();
    /*This test check only happy scenario, tests for negative scenario should be added*/
    @LocalServerPort
    private int serverPort;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TestRestTemplate restTemplate;
    private String uri;

    @PostConstruct
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        uri = "http://localhost:" + serverPort + "/task";
    }

    @Test
    public void getEmpty() {
        Task[] restArrayResult = getTasks();
        assertThat(restArrayResult).isEmpty();
    }

    @Test
    public void createOneTask() {
        createTasks(taskFirst);
        assertThatTaskResultContainsOnly(taskFirst);
    }

    @Test
    public void createTwoTask() {
        createTasks(taskFirst, taskSecond);
        assertThatTaskResultContainsOnly(taskFirst, taskSecond);
    }

    @Test
    public void updateTaskWhenTwoTaskAreCreated() {
        createTasks(taskFirst, taskSecond);

        Task taskFirstCreated = Arrays.stream(getTasks())
                .filter(e -> e.getTitle()
                        .equals(taskFirst.getTitle()))
                .findFirst()
                .orElseThrow();
        Task taskFirstUpdated = Task.builder().id(taskFirstCreated.getId()).title(taskFirstCreated.getTitle()).build();
        taskFirstUpdated.setId(taskFirst.getId());
        taskFirstUpdated.setTitle(taskFirstUpdated.getTitle() + "Hakuna matata");
        JsonPatch patch = JsonDiff.asJsonPatch(mapper.valueToTree(taskFirst), mapper.valueToTree(taskFirstUpdated));
        restTemplate.patchForObject(uri + "/" + taskFirstCreated.getId(), patch, Task.class);
        assertThatTaskResultContainsOnly(taskFirstUpdated, taskSecond);
    }


    @Test
    public void createTwoTasksAndRemoveBoth() {
        createTasks(taskFirst, taskSecond);

        Task[] tasks = getTasks();

        Task taskFirstCreated = Arrays.stream(tasks)
                .filter(e -> e.getTitle()
                        .equals(taskFirst.getTitle()))
                .findFirst()
                .orElseThrow();
        restTemplate.delete(uri + "/" + taskFirstCreated.getId());

        Task taskSecondCreated = Arrays.stream(tasks)
                .filter(e -> e.getTitle()
                        .equals(taskSecond.getTitle()))
                .findFirst()
                .orElseThrow();

        restTemplate.delete(uri + "/" + taskSecondCreated.getId());

        assertThatTaskResultIsEmpty();
    }

    @Test
    public void createOneTaskAndRemove() {
        createTasks(taskFirst);

        Task taskFirstCreated = Arrays.stream(getTasks())
                .filter(e -> e.getTitle()
                        .equals(taskFirst.getTitle()))
                .findFirst()
                .orElseThrow();
        restTemplate.delete(uri + "/" + taskFirstCreated.getId());
        assertThatTaskResultIsEmpty();
    }

    private void createTasks(Task... values) {
        Arrays.stream(values).forEach(task -> restTemplate.put(uri, task, Task.class));
    }

    private void assertThatTaskResultIsEmpty() {
        assertThat(getTasks()).isEmpty();
    }

    private void assertThatTaskResultContainsOnly(Task... values) {
        Task[] restArrayResult = getTasks();
        assertThat(restArrayResult)
                .isNotNull()
                .hasSize(values.length)
                .extracting("title")
                .contains(
                        Arrays.stream(values)
                                .map(Task::getTitle)
                                .toList()
                                .toArray());
        assertThat(
                Arrays.stream(restArrayResult)
                        .map(Task::getId)
                        .filter(id -> id <= 0)
                        .collect(Collectors.toList()))
                .isEmpty();
    }

    private Task[] getTasks() {
        return restTemplate.getForObject(uri, Task[].class);
    }
}
