package com.mavha.todo.taskresource.view.dto;

import com.mavha.todo.taskresource.persistence.entities.State;
import com.mavha.todo.taskresource.persistence.entities.Task;

public class TaskDTO {

  private Long id;
  private String description;
  private State state;
  private String imageUrl;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public State getState() {
    return state;
  }

  public void setState(State done) {
    this.state = done;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public static TaskDTO from(Task task) {
    TaskDTO taskDTO = new TaskDTO();
    taskDTO.setId(task.getId());
    taskDTO.setDescription(task.getDescription());
    taskDTO.setState(task.getState());
    taskDTO.setImageUrl(task.getImageUrl());
    return taskDTO;
  }

  public Task toTask() {
    Task task = new Task();
    task.setId(this.id);
    task.setDescription(this.description);
    task.setState(this.state);
    task.setImageUrl(this.imageUrl);
    return task;
  }

}
