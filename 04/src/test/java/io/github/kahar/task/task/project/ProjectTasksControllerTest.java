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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectTasksControllerTest extends AbstractControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskTestHelper taskTestHelper;

    @Autowired
    private ProjectTestHelper projectTestHelper;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTaskTestHelper helper;


    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        taskTestHelper.setup(serverPort, restTemplate);
        projectTestHelper.setup(serverPort, restTemplate);
        helper.setup(serverPort, restTemplate);
    }

    @BeforeEach
    public void dbClean() {
        taskTestHelper.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    public void getEmpty() {
        List<Long> projectTaskIds = helper.getprojectTasks(1L);
        assertThat(projectTaskIds).isEmpty();
    }

    @Test
    public void addOneTaskToOneProject() {
        Long taskId = taskTestHelper.createTask("unique");
        Long projectId = projectTestHelper.createProject("unique");
        helper.addTaskToProject(taskId, projectId);
        assertThat(helper.getprojectTasks(projectId))
                .isNotNull()
                .hasSize(1)
                .contains(taskId);
    }

    @Test
    public void addTwoTaskToOneProject() {
        Long firstTaskId = taskTestHelper.createTask("first");
        Long secondTaskId = taskTestHelper.createTask("second");
        Long projectId = projectTestHelper.createProject("unique");
        helper.addTasksToProject(projectId, firstTaskId, secondTaskId);
        assertThat(helper.getprojectTasks(projectId))
                .isNotNull()
                .hasSize(2)
                .contains(firstTaskId, secondTaskId);
    }

    @Test
    public void addTwoTaskToOneProjectAndRemoveOneByOne() {
        Long firstTaskId = taskTestHelper.createTask("first");
        Long secondTaskId = taskTestHelper.createTask("second");
        Long projectId = projectTestHelper.createProject("unique");
        helper.addTasksToProject(projectId, firstTaskId, secondTaskId);
        String uriSecond = helper.getUriTemplate().replace("{id}", projectId.toString()) + "/" + secondTaskId;
        restTemplate.delete(uriSecond);
        assertThat(helper.getprojectTasks(projectId))
                .isNotNull()
                .hasSize(1)
                .contains(firstTaskId);
        String uriFirst = helper.getUriTemplate().replace("{id}", projectId.toString()) + "/" + firstTaskId;
        restTemplate.delete(uriFirst);
        assertThat(helper.getprojectTasks(projectId))
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void addTwoTaskToOneProjectAndRemoveBothAtOnce() {
        Long firstTaskId = taskTestHelper.createTask("first");
        Long secondTaskId = taskTestHelper.createTask("second");
        Long projectId = projectTestHelper.createProject("unique");
        helper.addTasksToProject(projectId, firstTaskId, secondTaskId);
        String uri = helper.getUriTemplate().replace("{id}", projectId.toString()) + "/" + firstTaskId + "," + secondTaskId;
        restTemplate.delete(uri);
        assertThat(helper.getprojectTasks(projectId))
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void addOneAndRemoveOneTaskFromOneProject() {
        Long firstTaskId = taskTestHelper.createTask("first");
        Long secondTaskId = taskTestHelper.createTask("second");
        Long thirdTaskId = taskTestHelper.createTask("third");
        Long projectId = projectTestHelper.createProject("unique");
        helper.addTasksToProject(projectId, firstTaskId, secondTaskId);

        List<Long> currentProjectTaskIds = helper.getprojectTasks(projectId);
        List<Long> expectedProjectTaskIds = Arrays.asList(secondTaskId, thirdTaskId);

        JsonPatch patch = JsonDiff.asJsonPatch(mapper.valueToTree(currentProjectTaskIds), mapper.valueToTree(expectedProjectTaskIds));
        String uri = helper.getUriTemplate().replace("{id}", projectId.toString());
        restTemplate.patchForObject(uri, patch, Long[].class);

        assertThat(helper.getprojectTasks(projectId))
                .isNotNull()
                .hasSize(2)
                .contains(secondTaskId, thirdTaskId);
    }
}
