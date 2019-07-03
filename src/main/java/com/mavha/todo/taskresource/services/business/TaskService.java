package com.mavha.todo.taskresource.services.business;

import com.mavha.todo.taskresource.persistence.entities.State;
import com.mavha.todo.taskresource.persistence.entities.Task;
import com.mavha.todo.taskresource.persistence.repositories.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private static final String STATE = "state";
  private static final String DESCRIPTION = "description";
  private static final String ID = "id";

  private final TaskRepository taskRepository;

  @PersistenceContext
  private EntityManager entityManger;


  @Autowired
  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }


  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  public Task saveTask(Task task) {
    return taskRepository.save(task);
  }

  public Optional<Task> findById(Long id) {
    return taskRepository.findById(id);
  }

  /**
   * Service. Build query predicate dynamically.
   *
   * @param id : Long()
   * @param description : String()
   * @param state : State()
   * @return List<Task>
   */
  public List<Task> filterBy(Long id, String description, State state) {
    CriteriaBuilder criteriaBuilder = entityManger.getCriteriaBuilder();
    CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
    Root<Task> root = criteriaQuery.from(Task.class);
    List<Predicate> predicates = new ArrayList<>();

    if (Objects.nonNull(id)) {
      predicates.add(criteriaBuilder.equal(root.get(ID), id));
    }
    if (Objects.nonNull(description)) {
      predicates.add(criteriaBuilder.equal(root.get(DESCRIPTION), description));
    }
    if (Objects.nonNull(state)) {
      predicates.add(criteriaBuilder.equal(root.get(STATE), state));
    }
    return entityManger.createQuery(criteriaQuery
        .where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))))
        .getResultList();
  }

  /**
   * Service used to update an entity.
   *
   * @param id : String()
   * @param task : Task()
   *
   * @return Task : Object()
   */
  public Task updateById(Long id, Task task) {
    Task taskToUpdate = taskRepository.getOne(id);
    taskToUpdate.setState(task.getState());
    return this.saveTask(taskToUpdate);
  }

}
