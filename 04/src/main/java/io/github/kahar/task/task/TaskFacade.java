package io.github.kahar.task.task;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskRepository taskRepository;

    public List<Long> getProjectTaskIdsList(Long projectId) {
        return taskRepository.findIdsByProjectId(projectId);
    }

    @Transactional
    public void removeTasksFromProject(long projectId, List<Long> toDeleteIds) {
        getTasksByProjectIdAndIds(projectId, toDeleteIds).forEach(task -> task.setProjectId(null));
    }

    @Transactional
    public void addTasksToProject(long projectId, List<Long> toAddIds) {
        taskRepository.findAllById(toAddIds).forEach(task -> task.setProjectId(projectId));
    }

    public List<Task> getTasksByProjectIdAndIds(long projectId, List<Long> taskIds) {
        return StreamSupport.stream(
                        taskRepository
                                .findAllById(taskIds)
                                .spliterator(), false)
                .filter(task -> task.getProjectId() == projectId)
                .collect(Collectors.toList());
    }
}
