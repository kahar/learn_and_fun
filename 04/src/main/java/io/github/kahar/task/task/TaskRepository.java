package io.github.kahar.task.task;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TaskRepository extends CrudRepository<Task, Long> {
}