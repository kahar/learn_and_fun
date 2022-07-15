package io.github.kahar.task.task.project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProjectRepository extends CrudRepository<Project, Long> {
}