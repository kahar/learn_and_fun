package io.github.kahar.task.task.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectRepository projectRepository;

    private final ObjectMapper objectMapper;


    @GetMapping
    public List<Project> projects() {
        return IterableUtils.toList(projectRepository.findAll());
    }

    @PutMapping
    public Project createProject(@RequestBody Project project) {
        if (project.getId() != null) {
            project.setId(null);
        }
        return projectRepository.save(project);
    }

    @PatchMapping("/{id}")
    public Project updateProject(@PathVariable long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Project Project = projectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Project not found by id:" + id));
        Project ProjectPatched = applyPatchToProject(patch, Project);
        return projectRepository.save(ProjectPatched);
    }

    private Project applyPatchToProject(
            JsonPatch patch, Project project) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(project, JsonNode.class));
        return objectMapper.treeToValue(patched, Project.class);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteProject(@PathVariable long id) {
        io.github.kahar.task.task.project.Project project = projectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Project not found by id:" + id));
        projectRepository.deleteById(id);
    }
}
