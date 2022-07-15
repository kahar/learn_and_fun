package io.github.kahar.task.task.project;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectTaskTestHelper {

    private TestRestTemplate restTemplate;


    private String uriTemplate;

    public String getUriTemplate() {
        return uriTemplate;
    }

    public void setup(int serverPort, TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        uriTemplate = "http://localhost:" + serverPort + "/project/{id}/task";
    }

    public List<Long> getprojectTasks(Long projectId) {
        String uri = uriTemplate.replace("{id}", projectId.toString());
        return Arrays.asList(
                restTemplate.getForObject(uri, Long[].class));
    }

    public void addTaskToProject(Long taskId, Long projectId) {
        String uri = uriTemplate.replace("{id}", projectId.toString());
        List<Long> toAddIds = Collections.singletonList(taskId);
        restTemplate.postForEntity(uri, toAddIds, Long[].class);
    }

    public void addTasksToProject(Long projectId, Long... taskIds) {
        String uri = uriTemplate.replace("{id}", projectId.toString());
        List<Long> toAddIds = Arrays.stream(taskIds).collect(Collectors.toList());
        restTemplate.postForEntity(uri, toAddIds, Long[].class);
    }
}
