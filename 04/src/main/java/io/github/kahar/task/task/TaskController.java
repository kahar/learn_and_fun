package io.github.kahar.task.task;

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

@RestController
@RequiredArgsConstructor
class TaskController {
    private final TaskRepository taskRepository;

    private final ObjectMapper objectMapper;

    @GetMapping("/task")
    public Iterable<Task> tasks() {
        return IterableUtils.toList(taskRepository.findAll());
    }

    @PutMapping("/task")
    public Task createTask(@RequestBody Task task) {
        if (task.getId() != null) {
            task.setId(null);
        }
        return taskRepository.save(task);
    }

    @PatchMapping("/task/{id}")
    public Task updateTask(@PathVariable long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task not found by id:" + id));
        Task taskPatched = applyPatchToTask(patch, task);
        return taskRepository.save(taskPatched);
    }

    private Task applyPatchToTask(
            JsonPatch patch, Task task) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(task, JsonNode.class));
        return objectMapper.treeToValue(patched, Task.class);
    }

    @DeleteMapping("/task/{id}")
    @Transactional
    public void deleteTask(@PathVariable long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task not found by id:" + id));
        taskRepository.deleteById(id);
    }
}
