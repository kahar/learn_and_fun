package io.github.kahar.task.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class TaskTestHelper {

    @Autowired
    private TaskRepository taskRepository;

    private TestRestTemplate restTemplate;

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setup(int serverPort, TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        uri = "http://localhost:" + serverPort + "/task";
    }

    public void deleteAll() {
        taskRepository.deleteAll();
    }

    public void createTasks(Task... values) {
        Arrays.stream(values).forEach(task -> restTemplate.put(uri, task, Task.class));
    }

    public Long createTask(String uniqueString) {
        Task taskToCreate = Task.builder().title("Title " + uniqueString).build();
        restTemplate.put(uri, taskToCreate, Task.class);
        Optional<Task> createdTask = Arrays.stream(getTasks()).filter(task -> task.getTitle().equals(taskToCreate.getTitle())).findFirst();
        if (createdTask.isPresent()) {
            return createdTask.get().getId();
        }
        return 0L;
    }

    public void assertThatTaskResultIsEmpty() {
        assertThat(getTasks()).isEmpty();
    }

    public void assertThatTaskResultContainsOnly(Task... values) {
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

    public Task[] getTasks() {
        return restTemplate.getForObject(uri, Task[].class);
    }
}
