package io.github.kahar.task.task.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.github.kahar.task.task.TaskFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/project/{id}/task")
@RequiredArgsConstructor
public class ProjectTasksController {
    private final TaskFacade taskFacade;

    private final ObjectMapper objectMapper;

    @GetMapping
    public List<Long> projectTasks(@PathVariable long id) {
        return taskFacade.getProjectTaskIdsList(id);
    }

    @DeleteMapping("/{toDeleteIds}")
    public void removeTasksFromProject(@PathVariable("id") long id, @PathVariable("toDeleteIds") Long[] toDeleteIds) {
        taskFacade.removeTasksFromProject(id, Arrays.stream(toDeleteIds).toList());
    }

    @PostMapping
    public List<Long> addTasksToProject(@PathVariable long id, @RequestBody List<Long> toAddIds) {
        taskFacade.addTasksToProject(id, toAddIds);
        return taskFacade.getProjectTaskIdsList(id);
    }

    @PatchMapping
    public List<Long> patchProjectTasks(@PathVariable long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        List<Long> currentList = taskFacade.getProjectTaskIdsList(id);
        List<Long> patchedList = applyPatchToList(patch, currentList);
        List<Long> toAddIds = patchedList.stream().filter(patched -> !currentList.contains(patched)).toList();
        taskFacade.addTasksToProject(id, toAddIds);

        List<Long> toDeleteIds = currentList.stream().filter(current -> !patchedList.contains(current)).toList();
        taskFacade.removeTasksFromProject(id, toDeleteIds);
        return taskFacade.getProjectTaskIdsList(id);
    }

    private List<Long> applyPatchToList(
            JsonPatch patch, List<Long> currentList) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(currentList, JsonNode.class));
        return Arrays.asList(objectMapper.treeToValue(patched, Long[].class));
    }
}
