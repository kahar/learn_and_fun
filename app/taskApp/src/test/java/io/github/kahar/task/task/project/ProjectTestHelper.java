package io.github.kahar.task.task.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class ProjectTestHelper {

    @Autowired
    private ProjectRepository projectRepository;

    private TestRestTemplate restTemplate;


    private String uri;

    public String getUri() {
        return uri;
    }

    public void setup(int serverPort, TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        uri = "http://localhost:" + serverPort + "/project";
    }

    public void deleteAll() {
        projectRepository.deleteAll();
    }

    public void createProjects(Project... values) {
        Arrays.stream(values).forEach(project -> restTemplate.put(uri, project, Project.class));
    }

    public void assertThatProjectResultIsEmpty() {
        assertThat(getProjects()).isEmpty();
    }

    public void assertThatProjectResultContainsOnly(Project... values) {
        Project[] restArrayResult = getProjects();
        assertThat(restArrayResult)
                .isNotNull()
                .hasSize(values.length)
                .extracting("name")
                .contains(
                        Arrays.stream(values)
                                .map(Project::getName)
                                .toList()
                                .toArray());
        assertThat(
                Arrays.stream(restArrayResult)
                        .map(Project::getId)
                        .filter(id -> id <= 0)
                        .collect(Collectors.toList()))
                .isEmpty();
    }

    public Project[] getProjects() {
        return restTemplate.getForObject(uri, Project[].class);
    }


    public Long createProject(String uniqueString) {
        Project projectToCreate = Project.builder().name("Name " + uniqueString).build();
        restTemplate.put(uri, projectToCreate, Project.class);
        Optional<Project> createdProject = Arrays.stream(getProjects()).filter(task -> task.getName().equals(projectToCreate.getName())).findFirst();
        if (createdProject.isPresent()) {
            return createdProject.get().getId();
        }
        return 0L;
    }
}
