package io.github.kahar.task.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TaskRepository extends CrudRepository<Task, Long> {

    @Query(value = "select t.id from Task t WHERE t.projectId = ?1")
    List<Long> findIdsByProjectId(long projectId);
}