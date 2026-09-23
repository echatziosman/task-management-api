package spacenes._stproject.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import spacenes._stproject.core.utilities.exceptions.TaskNotFoundException;
import spacenes._stproject.core.utilities.results.DataResult;
import spacenes._stproject.core.utilities.results.Result;
import spacenes._stproject.dataAccess.abstracts.TaskRepository;
import spacenes._stproject.entities.concretes.Task;
import spacenes._stproject.entities.dtos.TaskRequest;
import spacenes._stproject.entities.dtos.TaskResponse;
import spacenes._stproject.entities.dtos.TaskUpdateRequest;

@ExtendWith(MockitoExtension.class)
public class TaskManagerTest {

	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private TaskManager taskManager;
	
	@Test
	void shouldReturnTasksWhenTitleExists () {
		
		Task task = new Task();
		
		task.setId(1L);
		task.setTitle("Test Task");
		task.setDescription("Test Description");
		task.setCompleted(false);
		
		//Arrange - PostgreSQL yerine Mockito Repository'e gitmesini söylüyoruz
		when(taskRepository.getByTitle("Test Task"))
		    .thenReturn(List.of(task));
		
		//Act - Gerçek TaskManager sınıfını çalıştırır
		DataResult<List<Task>> result = taskManager.getByTitle("Test Task");
		
		//Assert
		assertTrue(result.isSuccess());
		assertEquals(1, result.getData().size());
		assertEquals("Test Task", result.getData().get(0).getTitle());
		
		//Verify - TaskManager bu metodu getByTitle() çağırdı mı kontrol ediyoruz
		verify(taskRepository).getByTitle("Test Task");
		
		
	}
	
	@Test
	void shouldThrowExceptionWhenTitleDoesNotExist() {
		
		//Arrange
		when(taskRepository.getByTitle("Unknown Task"))
		       .thenReturn(List.of());
		
		//Act & Assert
		assertThrows(TaskNotFoundException.class, () -> taskManager.getByTitle("Unknown Task"));
	}
	
	@Test
	void shouldCreateTaskWithDto() {
		
		//Arrange
		TaskRequest request = new TaskRequest();
		
		request.setTitle("New Task");
		request.setDescription("New Description");
		
		Task savedTask = new Task();
		
		savedTask.setId(1L);
		savedTask.setTitle("New Task");
		savedTask.setDescription("New Description");
		savedTask.setCompleted(false);
		
		when(taskRepository.save(any(Task.class)))
		      .thenReturn(savedTask);
		
		//Act
		DataResult<TaskResponse> result = taskManager.createTaskWithDto(request);
		
		//Assert
		assertTrue(result.isSuccess());
		assertEquals(1L, result.getData().getId());
		assertEquals("New Task", result.getData().getTitle());
		assertEquals("New Description", result.getData().getDescription());
		assertEquals(false, result.getData().isCompleted());
		
		//Verify
		verify(taskRepository).save(any(Task.class));
		
		
	}
	
	@Test
	void shouldUpdateTaskWithDto() {
		
		//Arrange
		Task task = new Task();
		task.setId(1L);
		task.setTitle("Old Title");
		task.setDescription("Old Description");
		task.setCompleted(false);
		
		TaskUpdateRequest request = new TaskUpdateRequest();
		request.setTitle("Updated Title");
		request.setDescription(null);
		request.setCompleted(true);
		
		when(taskRepository.findById(1L))
		      .thenReturn(Optional.of(task));
		
		when(taskRepository.save(any(Task.class)))
		      .thenReturn(task);
		
		//Act
		DataResult<TaskResponse> result = taskManager.updateTaskWithDto(1L, request);
		
		//Assert
		assertTrue(result.isSuccess());
		
		assertEquals("Updated Title", result.getData().getTitle());
		assertEquals("Old Description", result.getData().getDescription());
		//assertEquals(true, result.getData().isCompleted());
		assertTrue(result.getData().isCompleted());
		
		
		//Verify
		verify(taskRepository).findById(1L);
		verify(taskRepository).save(task);
	}
	
	@Test
	void shouldThrowExceptionWhenUpdatingNonExistingTask() {
		
		//Arrange
		TaskUpdateRequest request = new TaskUpdateRequest();
		request.setTitle("Updated Title");
		
		when(taskRepository.findById(1L))
		      .thenReturn(Optional.empty());
		
		//Act & Assert
		assertThrows(TaskNotFoundException.class, () -> taskManager.updateTaskWithDto(1L, request));
		
		//Verify
		verify(taskRepository).findById(1L);
		verify(taskRepository, never()).save(any(Task.class));
	}
	
	@Test
	void shouldDeleteTask() {
		
		Task task = new Task();
		task.setId(1L);
	    task.setTitle("Task to Delete");
	    task.setDescription("Description");
	    task.setCompleted(false);
	    
	    when(taskRepository.findById(1L))
	           .thenReturn(Optional.of(task));
	    
	    Result result = taskManager.delete(1L);
	    
	    assertTrue(result.isSuccess());
	    assertEquals("Task successfully deleted", result.getMessage());
	    
	    verify(taskRepository).findById(1L);
	    verify(taskRepository).delete(task);
	  
	}
	
	@Test
	void shouldThrowExceptionWhenDeletingNonExistingTask() {
		
		when(taskRepository.findById(1L))
		     .thenReturn(Optional.empty());
		
		assertThrows(TaskNotFoundException.class, () -> taskManager.delete(1L));
		
		verify(taskRepository).findById(1L);
		verify(taskRepository, never()).delete(any(Task.class));
	}
}
