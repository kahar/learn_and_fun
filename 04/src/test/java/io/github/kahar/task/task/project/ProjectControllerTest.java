package io.github.kahar.task.task.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;
import io.github.kahar.task.AbstractControllerTest;
import io.github.kahar.task.task.TaskTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectControllerTest extends AbstractControllerTest {
    private final Project projectFirst = Project.builder().id(-1L).name("Name 1").build();
    private final Project projectSecond = Project.builder().id(-1L).name("Name 2").build();

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskTestHelper taskTestHelper;

    @Autowired
    private ProjectTestHelper helper;

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        taskTestHelper.setup(serverPort, restTemplate);
        helper.setup(serverPort, restTemplate);
    }

    @BeforeEach
    public void dbClean() {
        taskTestHelper.deleteAll();
        helper.deleteAll();
    }

    @Test
    public void getEmpty() {
        Project[] restArrayResult = helper.getProjects();
        assertThat(restArrayResult).isEmpty();
    }

    @Test
    public void createOneProject() {
        helper.createProjects(projectFirst);
        helper.assertThatProjectResultContainsOnly(projectFirst);
    }

    @Test
    public void createTwoProject() {
        helper.createProjects(projectFirst, projectSecond);
        helper.assertThatProjectResultContainsOnly(projectFirst, projectSecond);
    }

    @Test
    public void updateProjectWhenTwoProjectAreCreated() {
        helper.createProjects(projectFirst, projectSecond);

        Project taskFirstCreated = Arrays.stream(helper.getProjects())
                .filter(e -> e.getName()
                        .equals(projectFirst.getName()))
                .findFirst()
                .orElseThrow();
        Project taskFirstUpdated = Project.builder().id(taskFirstCreated.getId()).name(taskFirstCreated.getName()).build();
        taskFirstUpdated.setId(projectFirst.getId());
        taskFirstUpdated.setName(taskFirstUpdated.getName() + "Hakuna matata");
        JsonPatch patch = JsonDiff.asJsonPatch(mapper.valueToTree(projectFirst), mapper.valueToTree(taskFirstUpdated));
        restTemplate.patchForObject(helper.getUri() + "/" + taskFirstCreated.getId(), patch, Project.class);
        helper.assertThatProjectResultContainsOnly(taskFirstUpdated, projectSecond);
    }

    @Test
    public void createTwoProjectsAndRemoveBoth() {
        helper.createProjects(projectFirst, projectSecond);

        Project[] projects = helper.getProjects();

        Project taskFirstCreated = Arrays.stream(projects)
                .filter(e -> e.getName()
                        .equals(projectFirst.getName()))
                .findFirst()
                .orElseThrow();
        restTemplate.delete(helper.getUri() + "/" + taskFirstCreated.getId());

        Project taskSecondCreated = Arrays.stream(projects)
                .filter(e -> e.getName()
                        .equals(projectSecond.getName()))
                .findFirst()
                .orElseThrow();

        restTemplate.delete(helper.getUri() + "/" + taskSecondCreated.getId());

        helper.assertThatProjectResultIsEmpty();
    }

    @Test
    public void createOneProjectAndRemove() {
        helper.createProjects(projectFirst);

        Project taskFirstCreated = Arrays.stream(helper.getProjects())
                .filter(e -> e.getName()
                        .equals(projectFirst.getName()))
                .findFirst()
                .orElseThrow();
        restTemplate.delete(helper.getUri() + "/" + taskFirstCreated.getId());
        helper.assertThatProjectResultIsEmpty();
    }
}
