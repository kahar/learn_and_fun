clean, build and run whole project ( gradlew clean build bootRun  )

to run dockerized version with docker compose:  
gradlew clean build  
docker-compose up --build

to run dockerized version:  
gradlew clean build  
gradlew bootBuildImage --imageName=kahar.github.io/task-app  
docker run -p 8080:8080 -t kahar.github.io/task-app  

### Documentation of the decision

- There is hard decision to made: What should happen if user try to remove task from project to which this task was not
  assigned. I have made a conscious decision that no error will be thrown and incorrect data will be ignored/skipped.
- ProjectTasksController#addTasksToProject as Post: because this operation can remove task from one project and assign
  to another there is post. If this operation will only assign task to project, and throw exception in case of error,
  then probably there should be put.

TODO list

- Tests check in most cases only happy scenario, negative scenarios should be added, and also more positive scenarios
- add property base tests