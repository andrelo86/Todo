package com.mavha.todo.taskresource.services.exceptions;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String message) {
    super(message);
  }

}
