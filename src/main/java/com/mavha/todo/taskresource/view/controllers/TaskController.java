package com.mavha.todo.taskresource.view.controllers;

import static java.util.stream.Collectors.*;

import com.mavha.todo.taskresource.view.dto.TaskDTO;
import com.mavha.todo.taskresource.persistence.entities.State;
import com.mavha.todo.taskresource.persistence.entities.Task;
import com.mavha.todo.taskresource.services.business.TaskService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", headers = "Accept=application/json")
public class TaskController {

  private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);
  private final TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }


  @GetMapping(path = "/tasks/{id}")
  public @ResponseBody
  ResponseEntity<TaskDTO> searchTaskById(
      @PathVariable final Long id) {
    LOG.debug("Searching for a task with id: {}", id);
    Optional<Task> responseTask = taskService.findById(id);
    return responseTask.map(task -> new ResponseEntity<>(
        TaskDTO.from(task), HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/tasks")
  public @ResponseBody
  List<TaskDTO> search(
      @RequestParam(value = "id", required = false) final Long id,
      @RequestParam(value = "description", required = false) final String description,
      @RequestParam(value = "state", required = false) final State state) {
    LOG.debug("Searching tasks");
    return Objects.isNull(id) && Objects.isNull(description) && Objects.isNull(state) ? taskService
        .getAllTasks()
        .stream()
        .map(TaskDTO::from)
        .collect(toList()) : taskService.filterBy(id, description, state)
        .stream()
        .map(TaskDTO::from)
        .collect(toList());
  }

  @PostMapping(path = "/tasks")
  public @ResponseBody
  ResponseEntity<TaskDTO> createTask(@RequestBody final TaskDTO taskDTO) {
    LOG.debug("Creating new task..");
    return new ResponseEntity<>(TaskDTO.from(taskService.saveTask(taskDTO.toTask())),
        HttpStatus.OK);
  }

  @PatchMapping(path = "/tasks/{id}")
  public @ResponseBody
  ResponseEntity<TaskDTO> updateStatus(@PathVariable final Long id,
      @RequestBody final TaskDTO taskDTO) {
    LOG.debug("Updating state");
    return new ResponseEntity<>(TaskDTO.from(taskService.updateById(id, taskDTO.toTask())),
        HttpStatus.OK);
  }

}
