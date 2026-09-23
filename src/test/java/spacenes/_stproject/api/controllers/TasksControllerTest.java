package spacenes._stproject.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import spacenes._stproject.business.abstracts.TaskService;
import spacenes._stproject.core.utilities.results.DataResult;
import spacenes._stproject.core.utilities.results.Result;
import spacenes._stproject.core.utilities.results.SuccessDataResult;
import spacenes._stproject.core.utilities.results.SuccessResult;
import spacenes._stproject.entities.concretes.Task;
import spacenes._stproject.entities.dtos.TaskRequest;
import spacenes._stproject.entities.dtos.TaskResponse;
import spacenes._stproject.entities.dtos.TaskUpdateRequest;

@WebMvcTest(TasksController.class)
public class TasksControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private TaskService taskService;
	
	@Test
	void shouldReturnAllTasks() throws Exception {
		
		Task task = new Task();
		task.setId(1L);
		task.setTitle("Test Task");
		task.setDescription("Test Description");
		task.setCompleted(false);
		
		DataResult<List<Task>> response = new SuccessDataResult<>(List.of(task), "Data Listelendi");
		
		when(taskService.getAllTasks()).thenReturn(response);
		
		mockMvc.perform(get("/api/tasks/getall"))
		         .andExpect(status().isOk())
		         .andExpect(jsonPath("$.success").value(true))
		         .andExpect(jsonPath("$.data[0].title").value("Test Task"));
	}
	
	@Test
	void shouldReturnTasksByTitle() throws Exception {
		
		Task task = new Task();
		
		task.setId(1L);
		task.setTitle("Java");
		task.setDescription("Spring Boot Task");
		task.setCompleted(false);
		
		DataResult<List<Task>> response = new SuccessDataResult<List<Task>>(List.of(task), "Tasks Found");
		
		when(taskService.getByTitle("Java")).thenReturn(response);
		
		mockMvc.perform(get("/api/tasks/getByTitle")
				.param("title", "Java"))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.success").value("true"))
		        .andExpect(jsonPath("$.data[0].title").value("Java"));
	}
	
	@Test
	void shouldCreateTaskWithDto() throws Exception {
		
		TaskResponse taskResponse = new TaskResponse();
		
		taskResponse.setId(1L);
		taskResponse.setTitle("New Title");
		taskResponse.setDescription("New Description");
		taskResponse.setCompleted(false);
		
		DataResult<TaskResponse> response = new SuccessDataResult<TaskResponse>(taskResponse, "Dto'lu task eklendi");
		
		when(taskService.createTaskWithDto(any(TaskRequest.class))).thenReturn(response);
		
		mockMvc.perform(post("/api/tasks/createTaskWithDto")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						   "title": "New Title",
						   "description": "New Description"
						}
						"""))
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("$.success").value("true"))
		        .andExpect(jsonPath("$.data.title").value("New Title"))
		        .andExpect(jsonPath("$.data.completed").value("false"));
		
	}
	
	@Test
	void shouldUpdateTaskWithDto() throws Exception {
		
		TaskResponse taskResponse = new TaskResponse();
		
	    taskResponse.setId(1L);
	    taskResponse.setTitle("Updated Task");
	    taskResponse.setDescription("Updated Description");
	    taskResponse.setCompleted(true);

	    DataResult<TaskResponse> response =
	            new SuccessDataResult<>(taskResponse, "Task Update edildi");
	    
	    when(taskService.updateTaskWithDto(eq(1L), any(TaskUpdateRequest.class))).thenReturn(response);
	    
	    mockMvc.perform(patch("/api/tasks/1")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.content("""
	    				 {
	    				    "title": "Updated Task",
	    				    "description": "Updated Description",
	    				    "completed": "true"
	    				 }
	    				""" ))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.success").value(true))
	            .andExpect(jsonPath("$.data.id").value(1))
	            .andExpect(jsonPath("$.data.title").value("Updated Task"))
	            .andExpect(jsonPath("$.data.description").value("Updated Description"))
	            .andExpect(jsonPath("$.data.completed").value(true));
	}
	
	@Test
	void shouldDeleteTask() throws Exception {
		
		Result response = new SuccessResult("Task successfully deleted");
		
		when(taskService.delete(1L)).thenReturn(response);
		
		mockMvc.perform(delete("/api/tasks/1"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.success").value("true"))
		   .andExpect(jsonPath("$.message").value("Task successfully deleted"));
	}
	
	@Test
	void shouldReturnTasksByTitleContains() throws Exception {
		
		Task task = new Task();
		
	    task.setId(1L);
	    task.setTitle("Java Spring Boot");
	    task.setDescription("Backend project");
	    task.setCompleted(false);
	    
	    DataResult<List<Task>> response = new SuccessDataResult<>(List.of(task), "Task(s) found");
	    
	    when(taskService.getByTitleContains("Java")).thenReturn(response);
	    
	    mockMvc.perform(get("/api/tasks/getByTitleContains").param("title", "Java"))
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$.success").value("true"))
	          .andExpect(jsonPath("$.data[0].title").value("Java Spring Boot"));
	}
	
	@Test
	void shouldReturnBadRequestWhenTaskRequestIsInvalid() throws Exception {

	    mockMvc.perform(post("/api/tasks/createTaskWithDto")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("""
	                    {
	                        "title": "",
	                        "description": "Test Description"
	                    }
	                    """))
	            .andExpect(status().isBadRequest());
}
	}
