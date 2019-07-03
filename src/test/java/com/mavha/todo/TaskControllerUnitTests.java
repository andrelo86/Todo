package com.mavha.todo;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mavha.todo.taskresource.view.controllers.TaskController;
import com.mavha.todo.taskresource.view.dto.TaskDTO;
import com.mavha.todo.taskresource.persistence.entities.State;
import com.mavha.todo.taskresource.persistence.entities.Task;
import com.mavha.todo.taskresource.services.business.TaskService;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=TodoApplication.class)
@WebMvcTest(TaskController.class)
public class TaskControllerUnitTests {

  private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

  private List<TaskDTO> taskDTOList;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TaskService taskService;

	@Before
  public void loadTestData() {
	  LOG.info("Loading test data..");
    TaskDTO mockedDTO = new TaskDTO();
    mockedDTO.setId(1L);
    mockedDTO.setDescription("Testing units");
    mockedDTO.setState(State.PENDING);
    mockedDTO.setImageUrl(null);

    TaskDTO mockedDTO2 = new TaskDTO();
    mockedDTO2.setId(2L);
    mockedDTO2.setDescription("Testing units two");
    mockedDTO2.setState(State.PENDING);
    mockedDTO2.setImageUrl(null);

    TaskDTO mockedDTO3 = new TaskDTO();
    mockedDTO3.setId(3L);
    mockedDTO3.setDescription("Testing units three");
    mockedDTO3.setState(State.DONE);
    mockedDTO3.setImageUrl(null);
    this.taskDTOList = Arrays.asList(mockedDTO, mockedDTO2, mockedDTO3);
    LOG.info("Test data loaded.");
  }

	@Test
	public void checkGetAllTaskTest() throws Exception {

		List<Task> taskList = taskDTOList.stream()
        .map(TaskDTO::toTask)
        .collect(Collectors.toList());

		given(taskService.getAllTasks()).willReturn(taskList);

		mvc.perform(get("/api/tasks")
		  .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value(taskDTOList.get(0).getId()));
	}

  @Test()
  public void checkFindTaskByIdTest() throws Exception {
	  Task task = taskDTOList.get(1).toTask();

	  given(taskService.findById(2L)).willReturn(Optional.ofNullable(task));

    mvc.perform(get("/api/tasks?id=2")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(Objects.requireNonNull(task).getId()));
  }

  @Test()
  public void checkCreateNewTask() throws Exception {
    TaskDTO taskDTO = new TaskDTO();
    taskDTO.setId(3L);
    taskDTO.setDescription("new task");
    taskDTO.setState(State.PENDING);
    taskDTO.setImageUrl(null);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String requestJson=ow.writeValueAsString(taskDTO);

    given(taskService.saveTask(taskDTO.toTask())).willReturn(taskDTO.toTask());
    LOG.info(requestJson);
    mvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(requestJson));
  }

}
